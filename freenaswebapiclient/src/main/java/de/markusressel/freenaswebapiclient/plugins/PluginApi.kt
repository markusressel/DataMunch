package de.markusressel.freenaswebapiclient.plugins

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import io.reactivex.Single

interface PluginApi {
    /**
     * Get a list of all plugins
     */
    fun getPlugins(limit: Int = 20, offset: Int = 0): Single<List<PluginModel>>

    /**
     * Start a plugin
     */
    fun startPlugin(pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Stop a plugin
     */
    fun stopPlugin(pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Delete a plugin
     */
    fun deletePlugin(pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>
}