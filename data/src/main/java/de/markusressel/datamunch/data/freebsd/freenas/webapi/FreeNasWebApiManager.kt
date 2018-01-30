package de.markusressel.datamunch.data.freebsd.freenas.webapi

import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.JailJSON
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.PluginJSON
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.ServiceJSON
import de.markusressel.datamunch.data.ssh.ExecuteCommandResult
import de.markusressel.datamunch.data.ssh.SSHClient
import de.markusressel.datamunch.domain.SSHConnectionConfig
import javax.inject.Inject

/**
 * Created by Markus on 29.01.2018.
 */
class FreeNasWebApiManager @Inject constructor() {

    @Inject
    lateinit var sshClient: SSHClient

    val gson = Gson()

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
    fun setSSHConnectionConfig(vararg sshConnectionConfig: SSHConnectionConfig) {
        if (sshConnectionConfig.isEmpty()) {
            throw IllegalArgumentException("SSH Connection Config must not be empty!")
        }

        _sshConnectionConfigList = sshConnectionConfig
    }

    enum class HttpRequestType {
        GET,
        POST,
        PUT,
        DELETE
    }

    companion object {
        const val BASE_URL = "http://localhost/api/v1.0/"
    }

    protected fun executeSSHCommand(command: String): ExecuteCommandResult {
        return sshClient.executeCommand(
                *sshConnectionConfigList,
                command = command)
    }

    private fun executeWebRequest(type: HttpRequestType, subUrl: String): ExecuteCommandResult {
        // BASE_URL
        val requestType = when (type) {
            HttpRequestType.GET -> "GET"
            HttpRequestType.POST -> "POST"
            HttpRequestType.PUT -> "PUT"
            HttpRequestType.DELETE -> "DELETE"
        }

        val targetSystem = sshConnectionConfigList.last()
        val user = targetSystem.username
        val password = targetSystem.password

        val command = "curl -v -u $user:$password -X $requestType ${BASE_URL}$subUrl"

        return executeSSHCommand(command)
    }

    /**
     * Retrieve a list of all configured services
     */
    fun retrieveServices(): List<ServiceJSON> {
        val result = executeWebRequest(HttpRequestType.GET, "/services/services/")
        return gson.fromJson(result.output)
    }

    /**
     * Retrieve a list of all configured jails
     */
    fun retrieveJails(): List<JailJSON> {
        val result = executeWebRequest(HttpRequestType.GET, "/jails/jails/")
        return gson.fromJson(result.output)
    }

    /**
     * Start a jail
     */
    fun startJail(jailId: Long): ExecuteCommandResult {
        return executeWebRequest(HttpRequestType.POST, "/jails/jails/$jailId/start/")
    }

    /**
     * Stop a jail
     */
    fun stopJail(jailId: Long): ExecuteCommandResult {
        return executeWebRequest(HttpRequestType.POST, "/jails/jails/$jailId/stop/")
    }

    /**
     * Retrieve a list of all configured plugins
     */
    fun retrievePlugins(): List<PluginJSON> {
        val result = executeWebRequest(HttpRequestType.GET, "/plugins/plugins/")
        return gson.fromJson(result.output)
    }

}