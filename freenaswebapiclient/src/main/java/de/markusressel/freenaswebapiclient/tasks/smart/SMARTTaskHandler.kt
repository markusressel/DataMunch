package de.markusressel.freenaswebapiclient.tasks.smart

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class SMARTTaskHandler(private val requestManager: RequestManager) : SMARTTaskApi {
    override fun getSMARTTasks(limit: Int, offset: Int): Single<List<SMARTTaskModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/tasks/smarttest/", params, Method.GET,
                           SMARTTaskModel.ListDeserializer())
    }

    override fun createSMARTTask(data: SMARTTaskModel): Single<SMARTTaskModel> {
        return requestManager
                .doJsonRequest("/tasks/smarttest/", Method.POST, data,
                               SMARTTaskModel.SingleDeserializer())
    }

    override fun updateSMARTTask(data: SMARTTaskModel): Single<SMARTTaskModel> {
        return requestManager
                .doJsonRequest("/tasks/smarttest/${data.id}/", Method.PUT, data,
                               SMARTTaskModel.SingleDeserializer())
    }

    override fun deleteSMARTTask(
            data: SMARTTaskModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/tasks/smarttest/${data.id}/", Method.DELETE)
    }

}