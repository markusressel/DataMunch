package de.markusressel.datamunch.data

import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHClient
import de.markusressel.datamunch.domain.SSHConnectionConfig
import java.io.File
import javax.inject.Inject

/**
 * Created by Markus on 14.01.2018.
 */
abstract class ServerManager {

    @Inject
    lateinit var sshClient: SSHClient

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

    protected abstract fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult

    data class UptimeResult(val uptime: String,
                            val clock: String,
                            val users: Int,
                            val load1m: Float,
                            val load5m: Float,
                            val load15m: Float)

    fun uploadFile(vararg sshConnectionConfig: SSHConnectionConfig, file: File, destinationPath: String) {
        sshClient.uploadFile(*sshConnectionConfig, file = file, destinationPath = destinationPath)
    }

}

