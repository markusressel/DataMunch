package de.markusressel.freenaswebapiclient.services

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.services.service.ServiceApi
import de.markusressel.freenaswebapiclient.services.service.ServiceHandler

/**
 * Created by Markus on 09.02.2018.
 */
class ServicesManager(private val requestManager: RequestManager,
                      serviceApi: ServiceApi = ServiceHandler(requestManager)) :
    ServiceApi by serviceApi, ServicesApi