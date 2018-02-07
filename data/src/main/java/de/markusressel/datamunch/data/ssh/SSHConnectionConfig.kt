package de.markusressel.datamunch.data.ssh

/**
 * Class that represents a SSHConnectionConfig in the domain layer.
 *
 * Created by Markus on 06.01.2018.
 */
data class SSHConnectionConfig(
        val host: String,
        val port: Int = 22,
        val username: String,
        val password: String
)