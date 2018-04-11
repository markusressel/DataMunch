/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    fun writeToShell(vararg text: String) {
        outputStream
                ?.let {

                    val combinedText = text
                            .joinToString(separator = "")

                    val substrings = combinedText
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