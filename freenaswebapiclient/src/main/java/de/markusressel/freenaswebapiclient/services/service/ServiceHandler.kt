package de.markusressel.freenaswebapiclient.services.service

import com.github.kittinunf.fuel.core.Method
import com.github.salomonbrys.kotson.jsonObject
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class ServiceHandler(private val requestManager: RequestManager) : ServiceApi {

    override fun getServices(limit: Int, offset: Int): Single<List<ServiceModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/services/services/", params, Method.GET,
                           ServiceModel.ListDeserializer())
    }

    override fun updateService(serviceId: Int, srv_service: String,
                               srv_enable: Boolean): Single<ServiceModel> {
        val data = jsonObject("srv_service" to srv_service, "srv_enable" to srv_enable)
        return requestManager
                .doJsonRequest("/services/services/$serviceId/", Method.PUT, data,
                               ServiceModel.SingleDeserializer())
    }

}