package de.markusressel.datamunch.data.ssh

import com.github.ajalt.timberkt.Timber
import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.jcraft.jsch.UserInfo
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Markus on 13.01.2018.
 */
@Singleton
class SSHClient @Inject constructor() {

    private val sshClient: JSch = JSch()

    /**
     * Execute a SSH command
     *
     * @param host host address
     * @param port host port
     * @param user user name
     * @param password user password
     */
    fun executeCommand(proxyHost: String, proxyPort: Int = 22, proxyUser: String, proxyPassword: String,
                       targetHost: String, targetPort: Int = 22, targetUser: String, targetPassword: String,
                       command: String): String {
        val result: String = runOnTunneledSession(
                proxyHost = proxyHost,
                proxyPort = proxyPort,
                proxyUser = proxyUser,
                proxyPassword = proxyPassword,
                targetHost = targetHost,
                targetPort = targetPort,
                targetUser = targetUser,
                targetPassword = targetPassword) { proxySession: Session, targetSession: Session ->
            // connect the channel
            val channel = createExecChannel(targetSession, command)
            channel.connect()

            val inputStream = channel.inputStream

            var result = ""
            val tmp = ByteArray(1024)
            while (true) {

                while (inputStream.available() > 0) {
                    val i: Int = inputStream.read(tmp, 0, tmp.size)
                    if (i < 0) break

                    result += String(tmp, 0, i)
                }

                if (channel.isClosed) {
                    if (inputStream.available() > 0) continue

                    Timber.d { "Exit-Status: ${channel.exitStatus} Result: $result" }
                    break
                }
                try {
                    Thread.sleep(1000)
                } catch (ee: Exception) {
                }
            }

            channel.disconnect()

            result
        }

        return result
    }

    /**
     * Execute a SSH command
     *
     * @param host host address
     * @param port host port
     * @param user user name
     * @param password user password
     */
    fun executeCommand(host: String, port: Int = 22,
                       user: String, password: String,
                       command: String): String {

        val result = runOnSession(host, port, user, password) { session: Session ->

            // connect the channel
            val channel = createExecChannel(session, command)
            channel.connect()

            val inputStream = channel.inputStream

            var result = ""
            val tmp = ByteArray(1024)
            while (true) {

                while (inputStream.available() > 0) {
                    val i: Int = inputStream.read(tmp, 0, tmp.size)
                    if (i < 0) break

                    result += String(tmp, 0, i)
                }

                if (channel.isClosed) {
                    if (inputStream.available() > 0) continue

                    Timber.d { "Exit-Status: ${channel.exitStatus} Result: $result" }
                    break
                }
                try {
                    Thread.sleep(1000)
                } catch (ee: Exception) {
                }
            }

            channel.disconnect()

            result
        }


        return result
    }

    /**
     * Get a standard ssh session
     */
    private fun runOnSession(host: String, port: Int,
                             user: String, password: String,
                             run: (session: Session) -> String): String {
        val session = sshClient.getSession(user, host, port)

        session.userInfo = object : UserInfo {
            override fun promptPassphrase(message: String?): Boolean {
                return true
            }

            override fun getPassphrase(): String {
                return ""
            }

            override fun getPassword(): String {
                return password
            }

            override fun promptYesNo(message: String?): Boolean {
                return true
            }

            override fun showMessage(message: String?) {
            }

            override fun promptPassword(message: String?): Boolean {
                return true
            }

        }

        // connect to session
        session.connect()

        val result = run.invoke(session)

        session.disconnect()

        return result
    }

    /**
     * Run command on a tunneled ssh session
     */
    private fun runOnTunneledSession(proxyHost: String, proxyPort: Int, proxyUser: String, proxyPassword: String,
                                     targetHost: String, targetPort: Int, targetUser: String, targetPassword: String,
                                     run: (proxySession: Session, targetSession: Session) -> String): String {

        val proxyUserInformation = createUserInfo(proxyPassword)

        val proxySession = sshClient.getSession(proxyUser, proxyHost, proxyPort)
        proxySession.userInfo = proxyUserInformation
        proxySession.hostKeyAlias = proxyHost
        proxySession.setConfig("StrictHostKeyChecking", "no")
        // create port from assignedPort on local system to port targetPort on targetHost
        val assignedPort: Int = proxySession.setPortForwardingL(0, targetHost, targetPort)

        proxySession.connect()

        val targetUserInformation = createUserInfo(targetPassword)

        // create a session connected to port assignedPort on the local host.
        val targetSession = sshClient.getSession(targetUser, "localhost", assignedPort)
        targetSession.userInfo = targetUserInformation
        targetSession.hostKeyAlias = targetHost
        targetSession.setConfig("StrictHostKeyChecking", "no")

        targetSession.connect() // now we're connected to the secondary system

        // invoke the passed in lambda
        val result = run.invoke(proxySession, targetSession)

        targetSession.disconnect()
        proxySession.disconnect()

        return result
    }

    private fun createUserInfo(password: String): UserInfo {
        return object : UserInfo {
            override fun promptPassphrase(message: String?): Boolean {
                return true
            }

            override fun getPassphrase(): String {
                return ""
            }

            override fun getPassword(): String {
                return password
            }

            override fun promptYesNo(message: String?): Boolean {
                return true
            }

            override fun showMessage(message: String?) {
            }

            override fun promptPassword(message: String?): Boolean {
                return true
            }
        }
    }

    private fun createExecChannel(session: Session, command: String): ChannelExec {
        // open channel to work with
        val channel = session.openChannel(TYPE_EXEC) as ChannelExec

        channel.setCommand(command)

        channel.setInputStream(null)
        channel.setErrStream(System.err)

        return channel
    }

    companion object {
        val TYPE_EXEC = "exec"
    }

}