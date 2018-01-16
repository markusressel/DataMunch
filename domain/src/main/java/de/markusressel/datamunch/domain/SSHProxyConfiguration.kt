package de.markusressel.datamunch.domain

/**
 * Class that represents a SSH Proxy Configuration in the domain layer.
 *
 * Created by Markus on 06.01.2018.
 */
data class SSHProxyConfiguration(
        val host: String,
        val port: Int = 22,
        val username: String,
        val password: String
)