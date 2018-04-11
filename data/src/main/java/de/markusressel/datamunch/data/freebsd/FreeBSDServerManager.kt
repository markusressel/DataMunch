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

package de.markusressel.datamunch.data.freebsd

import de.markusressel.datamunch.data.ServerManager
import de.markusressel.datamunch.data.VirtualMachine
import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHConnectionConfig
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 14.01.2018.
 */
@Singleton
class FreeBSDServerManager @Inject constructor() : ServerManager() {

    override fun setSSHConnectionConfig(vararg sshConnectionConfig: SSHConnectionConfig) {
        super
                .setSSHConnectionConfig(*sshConnectionConfig)
    }

    /**
     * Retrieve OS version
     */
    fun retrieveReleaseVersion(): String {
        val commandResult = executeSSHCommand("uname -r")
        return commandResult
                .output
    }

    /**
     * Retrieve the Hardware Type/Platform
     */
    fun retrievePlatform(): String {
        val commandResult = executeSSHCommand("uname -m")
        return commandResult
                .output
    }

    /**
     * Retrieve the hostname of the specified server
     */
    fun retrieveHostname(): String {
        val commandResult = executeSSHCommand("/bin/hostname -s")
        return commandResult
                .output
    }

    override fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult {
        val trimmedResult = commandResult
                .output
                .trimIndent()

        val lines = trimmedResult
                .split(",")

        val uptime = lines[0]
                .trim()
        val clock = lines[1]
                .trim()

        val users = lines[2].trim().split(' ')[0]
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

    fun executeInJail(jailName: String, vararg commands: String): List<String> {
        return runInShell("jexec $jailName /bin/tcsh", *commands)
                .drop(1)
    }

}

