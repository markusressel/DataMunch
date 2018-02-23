package de.markusressel.freenaswebapiclient

import de.markusressel.freenaswebapiclient.account.AccountApi
import de.markusressel.freenaswebapiclient.account.AccountManager
import de.markusressel.freenaswebapiclient.jails.JailsApi
import de.markusressel.freenaswebapiclient.jails.JailsManager
import de.markusressel.freenaswebapiclient.plugins.PluginApi
import de.markusressel.freenaswebapiclient.plugins.PluginManager
import de.markusressel.freenaswebapiclient.services.ServicesApi
import de.markusressel.freenaswebapiclient.services.ServicesManager
import de.markusressel.freenaswebapiclient.sharing.SharingApi
import de.markusressel.freenaswebapiclient.sharing.SharingManager
import de.markusressel.freenaswebapiclient.storage.StorageApi
import de.markusressel.freenaswebapiclient.storage.StorageManager
import de.markusressel.freenaswebapiclient.system.SystemApi
import de.markusressel.freenaswebapiclient.system.SystemManager
import de.markusressel.freenaswebapiclient.tasks.TasksApi
import de.markusressel.freenaswebapiclient.tasks.TasksManager

/**
 * Convenience delegation class for easy access to all api methods
 *
 * Created by Markus on 06.02.2018.
 */
class FreeNasWebApiClient(private val requestManager: RequestManager = RequestManager(),
                          accountApi: AccountApi = AccountManager(requestManager),
                          jailsApi: JailsApi = JailsManager(requestManager),
                          servicesApi: ServicesApi = ServicesManager(requestManager),
                          sharingApi: SharingApi = SharingManager(requestManager),
                          storageApi: StorageApi = StorageManager(requestManager),
                          systemApi: SystemApi = SystemManager(requestManager),
                          pluginApi: PluginApi = PluginManager(requestManager),
                          tasksApi: TasksApi = TasksManager(requestManager)) :
    AccountApi by accountApi, JailsApi by jailsApi, ServicesApi by servicesApi,
    SharingApi by sharingApi, StorageApi by storageApi, SystemApi by systemApi,
    PluginApi by pluginApi, TasksApi by tasksApi {

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