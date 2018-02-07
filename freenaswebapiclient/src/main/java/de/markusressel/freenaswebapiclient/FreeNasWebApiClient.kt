package de.markusressel.freenaswebapiclient

import android.util.Log
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_response
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import de.markusressel.freenaswebapiclient.model.*
import io.reactivex.Single

/**
 * Created by Markus on 06.02.2018.
 */
class FreeNasWebApiClient(hostname: String = "localhost", apiResource: String = "api",
                          apiVersion: String = "1.0",
                          var basicAuthConfig: BasicAuthConfig? = null) {

    var hostname: String = hostname
        set(value) {
            field = value
            updateBaseUrl()
        }

    var apiResource: String = apiResource
        set(value) {
            field = value
            updateBaseUrl()
        }

    var apiVersion: String = apiVersion
        set(value) {
            field = value
            updateBaseUrl()
        }

    init {
        FuelManager
                .instance
                .addRequestInterceptor({ next: (Request) -> Request ->
                                           { t: Request ->
                                               Log
                                                       .d("Fuel", t.toString())
                                               next(t)
                                           }
                                       })
    }

    private fun updateBaseUrl() {
        FuelManager
                .instance
                .basePath = "https://$hostname/$apiResource/v$apiVersion"
    }

    /**
     * ====================================
     * Accounts
     * ====================================
     */

    /**
     * Get a list of all users
     */
    fun getUsers(limit: Int = DEFAULT_LIMIT,
                 offset: Int = DEFAULT_OFFSET): Single<List<UserModel>> {
        return doRequest("/account/users/".httpGet(), UserModel.ListDeserializer())
    }

    /**
     * Create a new user
     */
    fun createUser(data: UserModel): Single<UserModel> {
        return doJsonRequest("/account/users/".httpPost(), data, UserModel.SingleDeserializer())
    }

    /**
     * Update an existing user
     *
     * @param data user data
     */
    fun updateUser(data: UserModel): Single<UserModel> {
        return doJsonRequest("/account/users/${data.id}/".httpPut(), data,
                             UserModel.SingleDeserializer())
    }

    /**
     * Delete a mountpoint
     *
     * @param user the user
     */
    fun deleteUser(user: UserModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/account/users/${user.id}/".httpDelete())
    }

    /**
     * Set a password for a user
     */
    fun setUserPassword(userId: Long, newPassword: String): Single<String> {
        val passwordMap = mapOf(Pair("", newPassword))
        return doJsonRequest("/account/users/$userId/password/".httpPost(), passwordMap)
    }

    /**
     * Get user auxiliary groups
     */
    fun getGroups(userId: Long): Single<List<Long>> {
        throw NotImplementedError()
        //        return doRequest("/account/users/${userId}/".httpGet())
    }

    /**
     * Get a list of all users
     */
    fun getGroups(limit: Int = DEFAULT_LIMIT,
                  offset: Int = DEFAULT_OFFSET): Single<List<GroupModel>> {
        return doRequest("/account/groups/".httpGet(), GroupModel.ListDeserializer())
    }

    /**
     * ====================================
     * JAILS
     * ====================================
     */

    /**
     * Get a list of all jails
     */
    fun getJails(limit: Int = DEFAULT_LIMIT,
                 offset: Int = DEFAULT_OFFSET): Single<List<JailModel>> {
        return doRequest("/jails/jails/".httpGet(), JailModel.ListDeserializer())
    }

    /**
     * Create a jail
     */
    fun createJail(jailName: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        throw NotImplementedError()
    }

    /**
     * Start a jail
     */
    fun startJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/start/".httpPost())
    }

    /**
     * Stop a jail
     */
    fun stopJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/stop/".httpPost())
    }

    /**
     * Restart a jail
     */
    fun restartJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/restart/".httpPost())
    }

    /**
     * Delete a jail
     */
    fun deleteJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/".httpDelete())
    }

    /**
     * Get a list of all jail mountpoints
     */
    fun getMountpoints(limit: Int = DEFAULT_LIMIT,
                       offset: Int = DEFAULT_OFFSET): Single<List<MountpointModel>> {
        return doRequest("/jails/mountpoints/".httpGet(), MountpointModel.ListDeserializer())
    }

    /**
     * Create a new mountpoint
     */
    fun createMountpoint(data: MountpointModel): Single<MountpointModel> {
        return doJsonRequest("/jails/mountpoints/".httpPost(), data,
                             MountpointModel.SingleDeserializer())
    }

    /**
     * Update an existing mountpoint
     */
    fun updateMountpoint(data: MountpointModel): Single<MountpointModel> {
        return doJsonRequest("/jails/mountpoints/${data.id}/".httpPut(), data,
                             MountpointModel.SingleDeserializer())
    }

    /**
     * Delete a mountpoint
     */
    fun deleteMountpoint(
            mountpoint: MountpointModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/mountpoints/${mountpoint.id}/".httpDelete())
    }

    /**
     * Get a list of all templates
     */
    fun getTemplates(limit: Int = DEFAULT_LIMIT,
                     offset: Int = DEFAULT_OFFSET): Single<List<TemplateModel>> {
        return doRequest("/jails/templates/".httpGet(), TemplateModel.ListDeserializer())
    }

    /**
     * Create a new template
     */
    fun createTemplate(jt_arch: String, jt_instances: Long, jt_name: String, jt_os: String,
                       jt_url: String): Single<TemplateModel> {
        val data = TemplateModel(0, jt_arch, jt_instances, jt_name, jt_os, jt_url)
        return doJsonRequest("/jails/templates/".httpPost(), data,
                             TemplateModel.SingleDeserializer())
    }

    /**
     * Update an existing template
     */
    fun updateTemplate(id: Long, jt_arch: String, jt_instances: Long, jt_name: String,
                       jt_os: String, jt_url: String): Single<MountpointModel> {
        val data = TemplateModel(id, jt_arch, jt_instances, jt_name, jt_os, jt_url)
        return doJsonRequest("/jails/templates/$id/".httpPut(), data,
                             MountpointModel.SingleDeserializer())
    }

    /**
     * Delete a template
     */
    fun deleteTemplate(templateId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/templates/$templateId/".httpDelete())
    }

    /**
     * ====================================
     * SERVICES
     * ====================================
     */

    /**
     * Get a list of all services
     */
    fun getServices(limit: Int = 20, offset: Int = 0): Single<List<ServiceModel>> {
        return doRequest("/services/services/".httpGet(), ServiceModel.Deserializer())
    }

    /**
     * Get a list of all plugins
     */
    fun getPlugins(limit: Int = 20, offset: Int = 0): Single<List<PluginModel>> {
        return doRequest("/plugins/plugins/".httpGet(), PluginModel.Deserializer())
    }

    private fun doRequest(request: Request): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return getAuthenticatedRequest(request)
                .rx_response()
    }

    private fun <T : Any> doRequest(request: Request,
                                    deserializer: ResponseDeserializable<T>): Single<T> {
        return getAuthenticatedRequest(request)
                .rx_object(deserializer)
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    private fun <T : Any> doJsonRequest(request: Request, jsonData: Any,
                                        deserializer: ResponseDeserializable<T>): Single<T> {
        val json = Gson()
                .toJson(jsonData)

        return getAuthenticatedRequest(request)
                .body(json)
                .header(HEADER_CONTENT_TYPE_JSON)
                .rx_object(deserializer)
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    private fun doJsonRequest(request: Request, jsonData: Any): Single<String> {
        val json = Gson()
                .toJson(jsonData)

        return getAuthenticatedRequest(request)
                .body(json)
                .header(HEADER_CONTENT_TYPE_JSON)
                .rx_string()
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    private fun getAuthenticatedRequest(request: Request): Request {
        basicAuthConfig
                ?.let {
                    return request
                            .authenticate(username = it.username, password = it.password)
                }

        return request
    }

    companion object {
        val DEFAULT_LIMIT = 20
        val DEFAULT_OFFSET = 0

        val HEADER_CONTENT_TYPE_JSON = Pair("Content-Type", "application/json")
    }

}


