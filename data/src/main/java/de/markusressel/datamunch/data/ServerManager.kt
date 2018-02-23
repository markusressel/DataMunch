package de.markusressel.datamunch.data

import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHClient
import de.markusressel.datamunch.data.ssh.SSHConnectionConfig
import java.io.File
import javax.inject.Inject

/**
 * Created by Markus on 14.01.2018.
 */
abstract class ServerManager {

    @Inject
    lateinit var sshClient: SSHClient

    // backing property to allow getter override
    private var _sshConnectionConfigList: Array<out SSHConnectionConfig> = emptyArray()
    /**
     * Connection config
     */
    var sshConnectionConfigList: Array<out SSHConnectionConfig>
        get() {
            if (_sshConnectionConfigList.isEmpty()) {
                throw IllegalStateException("SSH Connection Config must not be empty!")
            } else {
                return _sshConnectionConfigList
            }
        }
        set(value) {
            if (value.isEmpty()) {
                throw IllegalArgumentException("SSH Connection Config must not be empty!")
            }
            _sshConnectionConfigList = value
        }

    /**
     * Set a ssh connection config for this manager
     */
    open fun setSSHConnectionConfig(vararg sshConnectionConfig: SSHConnectionConfig) {
        if (sshConnectionConfig.isEmpty()) {
            throw IllegalArgumentException("SSH Connection Config must not be empty!")
        }

        _sshConnectionConfigList = sshConnectionConfig
    }

    protected fun executeSSHCommand(command: String): ExecuteCommandResult {
        return sshClient
                .executeCommand(*sshConnectionConfigList, command = command)
    }

    protected fun runInShell(vararg commands: String): List<String> {
        return sshClient
                .runInShell(*sshConnectionConfigList, commands = commands.asList())
    }

    /**
     * Retrieve a list of all jails on this server
     */
    fun retrieveUptime(): UptimeResult {
        val result: ExecuteCommandResult = executeSSHCommand("uptime")
        return parseUptimeResult(result)
    }

    protected abstract fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult

    data class UptimeResult(val uptime: String, val clock: String, val users: Int,
                            val load1m: Float, val load5m: Float, val load15m: Float)

    /**
     * Get a list of all virtual machines on a server
     */
    protected abstract fun getVirtualMachines(): List<VirtualMachine>

    /**
     * Upload a file to a server
     */
    fun uploadFile(file: File, destinationPath: String) {
        sshClient
                .uploadFile(*sshConnectionConfigList, file = file,
                            destinationPath = destinationPath)
    }

}

