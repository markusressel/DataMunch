package de.markusressel.freenaswebapiclient.system.alert

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class AlertHandler(private val requestManager: RequestManager) : AlertApi {

    override fun getSystemAlerts(limit: Int, offset: Int): Single<List<AlertModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/system/alert/", params, Method.GET, AlertModel.ListDeserializer())
    }

    override fun dismissSystemAlert(
            alert: AlertModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/system/alert/${alert.id}/dismiss/", Method.GET)
    }

}