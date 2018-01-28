package de.markusressel.datamunch.data.freebsd

import de.markusressel.datamunch.data.ServerManager
import de.markusressel.datamunch.data.VirtualMachine
import de.markusressel.datamunch.data.freebsd.data.Jail
import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 14.01.2018.
 */
@Singleton
class FreeBSDServerManager @Inject constructor() : ServerManager() {

    /**
     * Retrieve OS version
     */
    fun retrieveReleaseVersion(): String {
        val commandResult = executeCommand("uname -r")
        return commandResult.output
    }

    /**
     * Retrieve the Hardware Type/Platform
     */
    fun retrievePlatform(): String {
        val commandResult = executeCommand("uname -m")
        return commandResult.output
    }

    /**
     * Retrieve the hostname of the specified server
     */
    fun retrieveHostname(): String {
        val commandResult = executeCommand("/bin/hostname -s")
        return commandResult.output
    }

    /**
     * Retrieve a list of all jails on this server
     */
    fun retrieveJails(): List<Jail> {
        val commandResult = executeCommand("jls")
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
                    Jail(id, name, path)
            )
        }

        return jails
    }

    /**
     * Start a jail
     *
     * @param jail the jail to start
     */
    fun startJail(jail: Jail): ExecuteCommandResult {
        val targetSystem = sshConnectionConfigList.last()
        val command = "curl -v -u ${targetSystem.username}:${targetSystem.password} -X POST http://localhost/api/v1.0/jails/jails/${jail.id}/start/"
        return executeCommand(command)
    }

    /**
     * Stop a jail
     *
     * @param jail the jail to stop
     */
    fun stopJail(jail: Jail): ExecuteCommandResult {
        val targetSystem = sshConnectionConfigList.last()
        val command = "curl -v -u ${targetSystem.username}:${targetSystem.password} -X POST http://localhost/api/v1.0/jails/jails/${jail.id}/stop/"
        return executeCommand(command)
    }

    /**
     * Restart a jail
     *
     * This will stop and start the jail.
     * It will not raise an error if the jail was already stopped.
     *
     * @param jail the jail to restart
     */
    fun restartJail(jail: Jail) {
        stopJail(jail)
        startJail(jail)
    }

    override fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult {
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

    override fun getVirtualMachines(): List<VirtualMachine> {
        return emptyList()
    }

}

