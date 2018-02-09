package de.markusressel.freenaswebapiclient

import android.util.Log
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.fuel.rx.rx_response
import com.github.kittinunf.fuel.rx.rx_string
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import io.reactivex.Single

/**
 * Created by Markus on 08.02.2018.
 */
class RequestManager(hostname: String = "localhost", apiResource: String = "api",
                     apiVersion: String = "1.0", var basicAuthConfig: BasicAuthConfig? = null) {

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

    private val fuelManager = FuelManager()

    init {
        updateBaseUrl()
        fuelManager
                .addRequestInterceptor({ next: (Request) -> Request ->
                                           { t: Request ->
                                               Log
                                                       .d("Fuel", t.toString())
                                               next(t)
                                           }
                                       })
    }

    private fun updateBaseUrl() {
        fuelManager
                .basePath = "https://$hostname/$apiResource/v$apiVersion"
    }

    private fun createRequest(url: String, method: Method): Request {
        return getAuthenticatedRequest(fuelManager.request(method, url))
    }

    private fun getAuthenticatedRequest(request: Request): Request {
        basicAuthConfig
                ?.let {
                    return request
                            .authenticate(username = it.username, password = it.password)
                }

        return request
    }

    fun doRequest(url: String,
                  method: Method): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return createRequest(url, method)
                .rx_response()
    }

    fun <T : Any> doRequest(url: String, method: Method,
                            deserializer: ResponseDeserializable<T>): Single<T> {
        return createRequest(url, method)
                .rx_object(deserializer)
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    fun <T : Any> doJsonRequest(url: String, method: Method, jsonData: Any,
                                deserializer: ResponseDeserializable<T>): Single<T> {
        val json = Gson()
                .toJson(jsonData)

        return createRequest(url, method)
                .body(json)
                .header(HEADER_CONTENT_TYPE_JSON)
                .rx_object(deserializer)
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    fun doJsonRequest(url: String, method: Method, jsonData: Any): Single<String> {
        val json = Gson()
                .toJson(jsonData)

        return createRequest(url, method)
                .body(json)
                .header(HEADER_CONTENT_TYPE_JSON)
                .rx_string()
                .map {
                    it.component1()
                            ?: throw it.component2()
                                    ?: throw Exception()
                }
    }

    companion object {
        const val DEFAULT_LIMIT = 20
        const val DEFAULT_OFFSET = 0

        val HEADER_CONTENT_TYPE_JSON = Pair("Content-Type", "application/json")
    }

}