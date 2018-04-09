package de.markusressel.datamunch.data.ssh

import de.markusressel.datamunch.data.persistence.AuthenticationPersistenceManager
import de.markusressel.datamunch.data.persistence.HostPersistenceManager
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
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    lateinit var hostPersistenceManager: HostPersistenceManager
    @Inject
    lateinit var authenticationPersistenceManager: AuthenticationPersistenceManager

    /**
     * Create an SSHConnectionConfig from the given parameters
     */
    private fun createSSHConnectionConfig(host: HostEntity, port: Int = 22,
                                          auth: AuthenticationEntity): SSHConnectionConfig {
        return SSHConnectionConfig(host = host.hostname, port = port, username = auth.username,
                                   password = auth.password)
    }

    fun getSSHProxy(): SSHConnectionConfig {
        return SSHConnectionConfig(
                host = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_HOST),
                port = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_PORT),
                username = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_USER),
                password = preferenceHandler.getValue(
                        PreferenceHandler.SSH_PROXY_PASSWORD))
    }

    fun getMainSSHConnection(): SSHConnectionConfig {
        var host = hostPersistenceManager
                .getActive()

        if (host == null) {
            host = HostEntity(0, preferenceHandler.getValue(PreferenceHandler.CONNECTION_HOST))
        }

        var auth = authenticationPersistenceManager
                .standardOperation()
                .all
                .firstOrNull()

        if (auth == null) {
            auth = AuthenticationEntity(0,
                                        username = preferenceHandler.getValue(
                                                PreferenceHandler.SSH_USER),
                                        password = preferenceHandler.getValue(
                                                PreferenceHandler.SSH_PASS))
        }

        return createSSHConnectionConfig(host = host, auth = auth)
    }

}