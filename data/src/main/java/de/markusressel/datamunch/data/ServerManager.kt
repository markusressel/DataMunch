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

package de.markusressel.datamunch.data

import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHClient
import de.markusressel.datamunch.data.ssh.SSHConnectionConfig
import java.io.File
import java.util.*
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

    /**
     * Executes shell commands
     *
     * @param commands commands to execute
     * @return list of command results
     */
    protected fun runInShell(vararg commands: String): SortedMap<String, String> {
        return sshClient
                .executeInShell(connectionConfig = *sshConnectionConfigList,
                                commands = commands.asList())
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

