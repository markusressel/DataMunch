package de.markusressel.freenaswebapiclient.services.service

import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_LIMIT
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_OFFSET
import io.reactivex.Single

interface ServiceApi {
    /**
     * Get a list of all services
     */
    fun getServices(limit: Int = DEFAULT_LIMIT,
                    offset: Int = DEFAULT_OFFSET): Single<List<ServiceModel>>

    /**
     * Update a service
     */
    fun updateServices(): Single<ServiceModel>
}