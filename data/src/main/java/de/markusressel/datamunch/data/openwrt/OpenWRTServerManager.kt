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

package de.markusressel.datamunch.data.openwrt

import de.markusressel.datamunch.data.ServerManager
import de.markusressel.datamunch.data.VirtualMachine
import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 14.01.2018.
 */
@Singleton
class OpenWRTServerManager @Inject constructor() : ServerManager() {

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

        val loadAverage1 = lines[2]
                .trim()
                .removePrefix("load average:")
                .trim()
                .toFloat()
        val loadAverage5 = lines[3]
                .trim()
                .toFloat()
        val loadAverage15 = lines[4]
                .trim()
                .toFloat()

        return UptimeResult(uptime, clock, -1, loadAverage1, loadAverage5, loadAverage15)
    }

    override fun getVirtualMachines(): List<VirtualMachine> {
        return emptyList()
    }

}

