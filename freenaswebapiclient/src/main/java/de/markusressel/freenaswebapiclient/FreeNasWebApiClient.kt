package de.markusressel.freenaswebapiclient

import android.util.Log
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.model.JailModel
import de.markusressel.freenaswebapiclient.model.PluginModel
import de.markusressel.freenaswebapiclient.model.ServiceModel
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Markus on 06.02.2018.
 */
class FreeNasWebApiClient(
        hostname: String = "localhost",
        apiResource: String = "api",
        apiVersion: String = "1.0",
        var basicAuthConfig: BasicAuthConfig? = null
) {

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
        FuelManager.instance.addRequestInterceptor({ next: (Request) -> Request ->
            { t: Request ->
                Log.d("Fuel", t.toString())
                next(t)
            }
        })
    }

    private fun updateBaseUrl() {
        FuelManager.instance.basePath = "https://$hostname/$apiResource/v$apiVersion"
    }

    /**
     * Get a list of all jails
     */
    fun getJails(): Single<List<JailModel>> {
        return doRequest("/jails/jails/".httpGet(), JailModel.Deserializer())
    }

    /**
     * Start a jail
     *
     * @param jailId the jail id
     */
    fun startJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/start/".httpPost())
    }

    /**
     * Stop a jail
     *
     * @param jailId the jail id
     */
    fun stopJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return doRequest("/jails/jails/$jailId/stop/".httpPost())
    }

    /**
     * Restart a jail
     *
     * @param jailId the jail id
     */
    fun restartJail(jailId: Long): Flowable<Pair<Response, Result<ByteArray, FuelError>>> {
        return stopJail(jailId)
                .concatWith { startJail(jailId) }
    }


    /**
     * Get a list of all services
     */
    fun getServices(): Single<List<ServiceModel>> {
        return doRequest("/services/services/".httpGet(), ServiceModel.Deserializer())
    }

    /**
     * Get a list of all plugins
     */
    fun getPlugins(): Single<List<PluginModel>> {
        return doRequest("/plugins/plugins/".httpGet(), PluginModel.Deserializer())
    }

    private fun doRequest(request: Request): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        val authenticatedRequest: Request = getAuthenticatedRequest(request)
        return authenticatedRequest.rx_response()
    }

    private fun <T : Any> doRequest(request: Request, deserializer: ResponseDeserializable<T>): Single<T> {
        val authenticatedRequest: Request = getAuthenticatedRequest(request)
        return authenticatedRequest.rx_object(deserializer)
                .map {
                    it.component1() ?: throw it.component2() ?: throw Exception()
                }
    }

    private fun getAuthenticatedRequest(request: Request): Request {
        basicAuthConfig?.let {
            return request.authenticate(
                    username = it.username,
                    password = it.password
            )
        }

        return request
    }

}


