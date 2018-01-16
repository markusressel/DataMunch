package de.markusressel.datamunch.data

import de.markusressel.datamunch.data.entity.Jail
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHClient
import de.markusressel.datamunch.domain.SSHConnectionConfig
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 14.01.2018.
 */
@Singleton
class FreeBSDServerManager @Inject constructor() {

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    lateinit var sshClient: SSHClient

    /**
     * Retrieve a list of all jails on this server
     */
    fun retrieveJails(vararg sshConnectionConfig: SSHConnectionConfig): List<Jail> {
        val commandResult = sshClient.executeCommand(
                *sshConnectionConfig,
                command = "jls")

        return parseJails(commandResult)
    }

    private fun parseJails(commandResult: ExecuteCommandResult): List<Jail> {
        val jails: MutableList<Jail> = LinkedList()

        val trimmedResult = commandResult.output.trimIndent()

        val lines = trimmedResult.split("\n")
        val header = lines[0]
        val linesWithoutHeader = lines.subList(1, lines.size)

        linesWithoutHeader.forEach {
            var snippet = it.trim()

            var i = snippet.indexOfFirst { it == ' ' }
            val id = snippet.substring(0, i).toInt()
            snippet = snippet.substring(i, snippet.length).trim()

            i = snippet.indexOfFirst { it == ' ' }
            val name = snippet.substring(0, i)
            snippet = snippet.substring(i, snippet.length).trim()

            val path = snippet.trim()

            jails.add(
                    Jail(id,
                            name,
                            path)
            )
        }

        return jails
    }

    /**
     * Retrieve a list of all jails on this server
     */
    fun retrieveUptime(vararg sshConnectionConfig: SSHConnectionConfig): UptimeResult {

        val command = "uptime"

        val result: ExecuteCommandResult =
                sshClient.executeCommand(
                        *sshConnectionConfig,
                        command = command)

        return parseUptimeResult(result)
    }

    private fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult {
        val trimmedResult = commandResult.output.trimIndent()

        val lines = trimmedResult.split(",")

        val uptime = lines[0].trim()
        val clock = lines[1].trim()

        val users = lines[2]
                .trim()
                .split(' ')[0]
                .toInt()

        val loadAverage1 = lines[3]
                .trim()
                .removePrefix("load averages:")
                .trim()
                .toFloat()
        val loadAverage5 = lines[4]
                .trim()
                .toFloat()
        val loadAverage15 = lines[5]
                .trim()
                .toFloat()


        return UptimeResult(uptime, clock, users, loadAverage1, loadAverage5, loadAverage15)
    }

    data class UptimeResult(val uptime: String,
                            val clock: String,
                            val users: Int,
                            val loadAverage1: Float,
                            val loadAverage5: Float,
                            val loadAverage15: Float)

}

