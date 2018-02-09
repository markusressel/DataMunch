package de.markusressel.freenaswebapiclient.plugins

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class PluginHandler(private val requestManager: RequestManager) : PluginApi {

    override fun getPlugins(limit: Int, offset: Int): Single<List<PluginModel>> {
        return requestManager
                .doRequest("/plugins/plugins/", Method.GET, PluginModel.Deserializer())
    }

    override fun startPlugin(pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/plugins/plugins/$pluginId/start/", Method.POST)
    }

    override fun stopPlugin(pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/plugins/plugins/$pluginId/stop/", Method.POST)
    }

    override fun deletePlugin(
            pluginId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/plugins/plugins/$pluginId/", Method.DELETE)
    }

}