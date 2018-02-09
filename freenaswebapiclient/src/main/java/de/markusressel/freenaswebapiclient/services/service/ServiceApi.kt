package de.markusressel.freenaswebapiclient.services.service

import io.reactivex.Single

interface ServiceApi {
    /**
     * Get a list of all services
     */
    fun getServices(limit: Int = 20, offset: Int = 0): Single<List<ServiceModel>>

    /**
     * Update a service
     */
    fun updateServices(): Single<ServiceModel>
}