package de.markusressel.freenaswebapiclient.plugins

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_LIMIT
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_OFFSET
import io.reactivex.Single

interface PluginApi {
    /**
     * Get a list of all plugins
     */
    fun getPlugins(limit: Int = DEFAULT_LIMIT,
                   offset: Int = DEFAULT_OFFSET): Single<List<PluginModel>>

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