package de.markusressel.datamunch.data.ssh

import de.markusressel.datamunch.data.persistence.entity.AuthenticationEntity
import de.markusressel.datamunch.data.persistence.entity.HostEntity
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 31.01.2018.
 */
@Singleton
class ConnectionManager @Inject constructor() {

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    /**
     * Get an SSHConnectionConfig from the given parameters
     */
    private fun getSSHConnectionConfig(host: HostEntity, port: Int = 22,
                                       auth: AuthenticationEntity): SSHConnectionConfig {
        return SSHConnectionConfig(host = host.hostname, port = port, username = auth.username, password = auth.password)
    }

    fun getSSHProxy(): SSHConnectionConfig {
        return SSHConnectionConfig(host = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_HOST), port = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_PORT), username = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_USER), password = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_PASSWORD))
    }

    fun getMainSSHConnection(): SSHConnectionConfig {
        return getSSHConnectionConfig(host = HostEntity(0, preferenceHandler.getValue(PreferenceHandler.CONNECTION_HOST), isActive = true), auth = AuthenticationEntity(0, username = preferenceHandler.getValue(PreferenceHandler.SSH_USER), password = preferenceHandler.getValue(PreferenceHandler.SSH_PASS)))
    }

}