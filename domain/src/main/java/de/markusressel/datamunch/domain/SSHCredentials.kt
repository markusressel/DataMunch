package de.markusressel.datamunch.domain

/**
 * Class that represents a User in the domain layer.
 *
 * Created by Markus on 06.01.2018.
 */
data class SSHCredentials(
        val username: String,
        val password: String
)