package de.markusressel.freenaswebapiclient.services.service

import com.github.kittinunf.fuel.core.Method
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class ServiceHandler(private val requestManager: RequestManager) : ServiceApi {

    /**
     * Get a list of all services
     */
    override fun getServices(limit: Int, offset: Int): Single<List<ServiceModel>> {
        return requestManager
                .doRequest("/services/services/", Method.GET, ServiceModel.Deserializer())
    }

    /**
     * Update a service
     */
    override fun updateServices(): Single<ServiceModel> {
        throw NotImplementedError()
    }

}