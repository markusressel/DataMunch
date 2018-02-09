package de.markusressel.freenaswebapiclient.jails.configuration

import com.github.kittinunf.fuel.core.Method
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class ConfigurationHandler(private val requestManager: RequestManager) : ConfigurationApi {

    override fun getJailsConfiguration(limit: Int, offset: Int): Single<JailConfigurationModel> {
        return requestManager
                .doRequest("/jails/configuration/", Method.GET,
                           JailConfigurationModel.SingleDeserializer())
    }

    override fun setJailsConfiguration(
            data: JailConfigurationModel): Single<JailConfigurationModel> {
        return requestManager
                .doJsonRequest("/jails/configuration/", Method.PUT, data,
                               JailConfigurationModel.SingleDeserializer())
    }

}