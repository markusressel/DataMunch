package de.markusressel.freenaswebapiclient.jails.jail

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class JailHandler(private val requestManager: RequestManager) : JailApi {

    override fun getJails(limit: Int, offset: Int): Single<List<JailModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/jails/jails/", params, Method.GET, JailModel.ListDeserializer())
    }

    override fun createJail(data: JailModel): Single<JailModel> {
        return requestManager
                .doJsonRequest("/jails/jails/", Method.POST, data, JailModel.SingleDeserializer())
    }

    override fun startJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/jails/$jailId/start/", Method.POST)
    }

    override fun stopJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/jails/$jailId/stop/", Method.POST)
    }

    override fun restartJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/jails/$jailId/restart/", Method.POST)
    }

    override fun deleteJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/jails/$jailId/", Method.DELETE)
    }

}