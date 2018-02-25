package de.markusressel.datamunch.data.ssh

import com.github.ajalt.timberkt.Timber
import com.jcraft.jsch.*
import java.io.BufferedWriter
import java.io.File
import java.io.InputStream
import java.io.OutputStreamWriter
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
    fun executeCommand(vararg connectionConfig: SSHConnectionConfig,
                       command: String): ExecuteCommandResult {
        val result: ExecuteCommandResult = runOnSession(*connectionConfig) { session: Session ->

            // connect the channel
            val channel = createExecChannel(session, command)
            channel
                    .connect()

            val inputStream = channel
                    .inputStream

            val result = readResponse(channel, inputStream)

            channel
                    .disconnect()

            ExecuteCommandResult(channel.exitStatus, result)
        }

        return result
    }

    /**
     * Get a shell
     */
    fun runInShell(vararg connectionConfig: SSHConnectionConfig,
                   commands: List<String>): List<String> {
        val results: List<String> = runOnSession(*connectionConfig) { session: Session ->

            // connect the channel
            val channel = createShellChannel(session)
            channel
                    .connect()

            val inputStream = channel
                    .inputStream
            val out = BufferedWriter(OutputStreamWriter(channel.outputStream))

            val results: MutableList<String> = mutableListOf()

            // initial response can be ignored as it's just the "welcome" message and initial prompt
            val initialResponse = readResponse(channel, inputStream)

            // process every command one after the other
            commands
                    .forEach {
                        // enter next command
                        out
                                .write(it)
                        out
                                .newLine()
                        out
                                .flush()

                        val response = readResponse(channel, inputStream)

                        // cleanup output (because it also contains the input)
                        val resultText = response
                                .removePrefix(it)
                                .trim()
                        // add the result to the return list
                        results
                                .add(resultText)
                    }

            channel
                    .disconnect()

            results
        }

        return results
    }

    private fun <T> runOnSession(vararg sshConnectionConfig: SSHConnectionConfig,
                                 run: (session: Session) -> T): T {

        // create session
        val sessions = createAndConnectSession(*sshConnectionConfig)

        // run commands
        val result = run
                .invoke(sessions.last())

        // disconnect session
        disconnectSession(sessions)

        return result
    }

    internal fun createAndConnectSession(
            vararg sshConnectionConfig: SSHConnectionConfig): List<Session> {
        if (sshConnectionConfig.isEmpty()) {
            throw IllegalArgumentException("There must be at least one ssh connection configuration!")
        }

        val sessions: MutableList<Session> = ArrayList()

        val firstHop = sshConnectionConfig[0]

        val proxySession = sshClient
                .getSession(firstHop.username, firstHop.host, firstHop.port)
        proxySession
                .userInfo = createUserInfo(firstHop.password)
        proxySession
                .hostKeyAlias = firstHop
                .host
        proxySession
                .setConfig("StrictHostKeyChecking", "no")
        proxySession
                .connect()

        // remember sessions
        sessions
                .add(proxySession)

        var currentSession: Session = proxySession
        for (config in sshConnectionConfig.drop(1)) {

            // create port from assignedPort on local system to port targetPort on targetHost
            val assignedPort: Int = currentSession
                    .setPortForwardingL(0, config.host, config.port)

            currentSession = sshClient
                    .getSession(config.username, "localhost", assignedPort)

            currentSession
                    .userInfo = createUserInfo(config.password)
            currentSession
                    .hostKeyAlias = config
                    .host
            currentSession
                    .setConfig("StrictHostKeyChecking", "no")

            currentSession
                    .connect()

            // remember sessions
            sessions
                    .add(currentSession)
        }

        return sessions
    }

    internal fun disconnectSession(sessions: List<Session>) {
        for (session in sessions.reversed()) {
            session
                    .disconnect()
        }
    }

    private fun readResponse(channel: Channel, inputStream: InputStream): String {
        var result = ""
        val tmp = ByteArray(1024)

        while (result.isEmpty()) {
            while (inputStream.available() > 0) {
                val i: Int = inputStream
                        .read(tmp, 0, tmp.size)
                if (i < 0) break

                result += String(tmp, 0, i)
            }

            if (channel.isClosed) {
                if (inputStream.available() > 0) continue

                Timber
                        .e { "Exit-Status: ${channel.exitStatus} Result: $result" }
                break
            }
            try {
                Thread
                        .sleep(100)
            } catch (ee: Exception) {
                Timber
                        .e(ee)
            }
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

        channel
                .setCommand(command)

        channel
                .inputStream = null
        channel
                .setErrStream(System.err)

        return channel
    }

    internal fun createShellChannel(session: Session): Channel {
        return session.openChannel(TYPE_SHELL) as Channel
    }

    private fun createSFTPChannel(session: Session): ChannelSftp {
        return session.openChannel(TYPE_SFTP) as ChannelSftp
    }

    companion object {
        const val TYPE_EXEC = "exec"
        const val TYPE_SHELL = "shell"

        const val TYPE_SFTP = "sftp"

    }

    /**
     * Upload a file to the specified ssh server
     */
    fun uploadFile(vararg sshConnectionConfig: SSHConnectionConfig, file: File,
                   destinationPath: String) {
        runOnSession(*sshConnectionConfig) { session: Session ->
            val channel = createSFTPChannel(session)
            channel
                    .connect()

            val mode = ChannelSftp
                    .OVERWRITE
            //            val mode = ChannelSftp.RESUME
            //            val mode = ChannelSftp.APPEND

            val monitor = object : SftpProgressMonitor {
                var count: Long = 0
                var max: Long = 0

                override fun init(op: Int, src: String?, dest: String?, max: Long) {
                    Timber
                            .d { "Upload init" }
                    this
                            .max = max
                    count = 0
                }

                override fun count(count: Long): Boolean {
                    this
                            .count = count
                    Timber
                            .d { "Upload count: $count/$max" }
                    return true
                }

                override fun end() {
                    Timber
                            .d { "Upload finished" }
                }
            }

            //            channel.mkdir(destinationPath)

            channel
                    .put(file.inputStream(), destinationPath, monitor, mode)
        }
    }

}

data class ExecuteCommandResult(val returnCode: Int, val output: String)
