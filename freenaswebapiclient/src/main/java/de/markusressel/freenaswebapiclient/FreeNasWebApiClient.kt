package de.markusressel.freenaswebapiclient

import de.markusressel.freenaswebapiclient.account.AccountApi
import de.markusressel.freenaswebapiclient.account.AccountManager
import de.markusressel.freenaswebapiclient.jails.JailsApi
import de.markusressel.freenaswebapiclient.jails.JailsManager
import de.markusressel.freenaswebapiclient.plugins.PluginApi
import de.markusressel.freenaswebapiclient.plugins.PluginHandler
import de.markusressel.freenaswebapiclient.services.ServicesApi
import de.markusressel.freenaswebapiclient.services.ServicesManager
import de.markusressel.freenaswebapiclient.storage.StorageApi
import de.markusressel.freenaswebapiclient.storage.StorageManager

/**
 * Convenience delegation class for easy access to all api methods
 *
 * Created by Markus on 06.02.2018.
 */
class FreeNasWebApiClient(private val requestManager: RequestManager = RequestManager(),
                          accountApi: AccountApi = AccountManager(requestManager),
                          jailsApi: JailsApi = JailsManager(requestManager),
                          servicesApi: ServicesApi = ServicesManager(requestManager),
                          storageApi: StorageApi = StorageManager(requestManager),
                          pluginApi: PluginApi = PluginHandler(requestManager)) :
    AccountApi by accountApi, JailsApi by jailsApi, ServicesApi by servicesApi,
    StorageApi by storageApi, PluginApi by pluginApi {

    /**
     * Set the hostname for this client
     */
    fun setHostname(hostname: String) {
        requestManager
                .hostname = hostname
    }

    /**
     * Set the api resource for this client (in case it is not the default "/api/")
     */
    fun setApiResource(apiResource: String) {
        requestManager
                .apiResource = apiResource
    }

    /**
     * Set the api version for this client
     */
    fun setapiVersion(apiVersion: String) {
        requestManager
                .apiVersion = apiVersion
    }

    /**
     * Set the BasicAuthConfig for this client
     */
    fun setBasicAuthConfig(basicAuthConfig: BasicAuthConfig) {
        requestManager
                .basicAuthConfig = basicAuthConfig
    }

}