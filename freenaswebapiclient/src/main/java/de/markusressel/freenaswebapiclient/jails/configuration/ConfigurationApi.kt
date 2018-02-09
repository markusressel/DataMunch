package de.markusressel.freenaswebapiclient.jails.configuration

import io.reactivex.Single

interface ConfigurationApi {
    /**
     * Get the (common) jail configuration
     */
    fun getJailsConfiguration(): Single<JailConfigurationModel>

    /**
     * Set the (common) jail configuration
     */
    fun setJailsConfiguration(data: JailConfigurationModel): Single<JailConfigurationModel>
}