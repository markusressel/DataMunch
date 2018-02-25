package de.markusressel.datamunch.data.ssh

import com.jcraft.jsch.Session
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStreamWriter

/**
 * Create a shell instance for continuous interaction
 */
class SSHShell(private vararg val sshConnectionConfig: SSHConnectionConfig,
               private val sshClient: SSHClient) {


    private var sessions: List<Session> = emptyList()

    private var outputStream: BufferedWriter? = null

    /**
     * Create a session
     */
    fun connect(): InputStream? {
        // create a session (through tunnel if necessary)
        sessions = sshClient
                .createAndConnectSession(*sshConnectionConfig)

        // acquire target session
        val targetSession = sessions
                .last()

        // create shell channel
        val channel = sshClient
                .createShellChannel(targetSession)
        channel
                .connect()

        outputStream = BufferedWriter(OutputStreamWriter(channel.outputStream))

        return channel.inputStream
                ?: throw IllegalStateException("Missing InputStream for shell")
    }

    /**
     * Check if the session is connected
     */
    fun isConnected() = outputStream != null

    /**
     * Write something to the shell
     */
    fun writeToShell(text: String) {
        outputStream
                ?.let {

                    val substrings = text
                            .split(Regex.fromLiteral("\r\n"))

                    substrings
                            .forEachIndexed { index, substring ->
                                it
                                        .write(substring)
                                if (index != substrings.lastIndex) {
                                    it
                                            .newLine()
                                }
                            }

                    it
                            .flush()
                }
    }

    fun backspace() {
        outputStream
                ?.let {
                    it
                            .write("\b")
                    it
                            .flush()
                }
    }

    /**
     * Disconnect the session
     */
    fun disconnect() {
        sshClient
                .disconnectSession(sessions)
        outputStream = null
    }

}