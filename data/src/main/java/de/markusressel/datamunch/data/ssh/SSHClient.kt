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
    fun executeCommand(host: String, port: Int = 22,
                       user: String, password: String,
                       command: String): String {

        val session: Session = sshClient.getSession(user, host, port)

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
        session.disconnect()

        return result
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