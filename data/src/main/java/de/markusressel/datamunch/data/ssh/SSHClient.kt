package de.markusressel.datamunch.data.ssh

import com.github.ajalt.timberkt.Timber
import com.jcraft.jsch.*
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Markus on 13.01.2018.
 */
@Singleton
class SSHClient @Inject constructor() {

    private val sshClient: JSch = JSch()

    /**
     * Execute a SSH command and retrieve it's output
     *
     * @param connectionConfig the connection configuration
     * @param command the command to execute
     *
     * @return the command output
     */
    fun executeCommand(vararg connectionConfig: SSHConnectionConfig, command: String): ExecuteCommandResult {
        val result: ExecuteCommandResult = runOnSession(*connectionConfig) { session: Session ->

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

            ExecuteCommandResult(channel.exitStatus, result)
        }

        return result
    }

    private fun <T> runOnSession(vararg sshConnectionConfig: SSHConnectionConfig, run: (session: Session) -> T): T {
        if (sshConnectionConfig.isEmpty()) {
            throw IllegalArgumentException("There must be at least one ssh conection configuration!")
        }

        val sessions: MutableList<Session> = ArrayList()

        val firstHop = sshConnectionConfig[0]

        val proxySession = sshClient.getSession(firstHop.username, firstHop.host, firstHop.port)
        proxySession.userInfo = createUserInfo(firstHop.password)
        proxySession.hostKeyAlias = firstHop.host
        proxySession.setConfig("StrictHostKeyChecking", "no")
        proxySession.connect()

        // remember sessions
        sessions.add(proxySession)

        var currentSession: Session = proxySession
        for (config in sshConnectionConfig.drop(1)) {

            // create port from assignedPort on local system to port targetPort on targetHost
            val assignedPort: Int = currentSession.setPortForwardingL(0, config.host, config.port)

            currentSession = sshClient.getSession(config.username, "localhost", assignedPort)

            currentSession.userInfo = createUserInfo(config.password)
            currentSession.hostKeyAlias = config.host
            currentSession.setConfig("StrictHostKeyChecking", "no")

            currentSession.connect()

            // remember sessions
            sessions.add(currentSession)
        }

        val result = run.invoke(currentSession)

        for (session in sessions.reversed()) {
            session.disconnect()
        }

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

    private fun createSFTPChannel(session: Session): ChannelSftp {
        // open channel to work with
        val channel = session.openChannel(TYPE_SFTP) as ChannelSftp
        return channel
    }

    companion object {
        val TYPE_EXEC = "exec"
        val TYPE_SFTP = "sftp"
    }

    /**
     * Upload a file to the specified ssh server
     */
    fun uploadFile(vararg sshConnectionConfig: SSHConnectionConfig, file: File, destinationPath: String) {
        runOnSession(*sshConnectionConfig) { session: Session ->
            val channel = createSFTPChannel(session)
            channel.connect()

            val mode = ChannelSftp.OVERWRITE
//            val mode = ChannelSftp.RESUME
//            val mode = ChannelSftp.APPEND

            val monitor = object : SftpProgressMonitor {
                var count: Long = 0
                var max: Long = 0

                override fun init(op: Int, src: String?, dest: String?, max: Long) {
                    Timber.d { "Upload init" }
                    this.max = max
                    count = 0
                }

                override fun count(count: Long): Boolean {
                    this.count = count
                    Timber.d { "Upload count: $count/$max" }
                    return true
                }

                override fun end() {
                    Timber.d { "Upload finished" }
                }
            }

//            channel.mkdir(destinationPath)

            channel.put(file.inputStream(), destinationPath, monitor, mode)
        }
    }

}

data class ExecuteCommandResult(
        val returnCode: Int,
        val output: String
)