package de.markusressel.datamunch.data

import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 14.01.2018.
 */
@Singleton
class OpenWRTServerManager @Inject constructor() : ServerManager() {

    override fun parseUptimeResult(commandResult: ExecuteCommandResult): UptimeResult {
        val trimmedResult = commandResult.output.trimIndent()

        val lines = trimmedResult.split(",")

        val uptime = lines[0].trim()
        val clock = lines[1].trim()

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

}

