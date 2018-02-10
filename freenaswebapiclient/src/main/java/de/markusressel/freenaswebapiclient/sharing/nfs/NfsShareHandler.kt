package de.markusressel.freenaswebapiclient.sharing.nfs

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
class NfsShareHandler(private val requestManager: RequestManager) : NfsApi {

    override fun getNfsShares(limit: Int, offset: Int): Single<List<NfsShareModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/sharing/nfs/", params, Method.GET, NfsShareModel.ListDeserializer())
    }

    override fun createNfsShare(data: NfsShareModel): Single<NfsShareModel> {
        return requestManager
                .doJsonRequest("/sharing/nfs/", Method.POST, data,
                               NfsShareModel.SingleDeserializer())
    }

    override fun updateNfsShare(data: NfsShareModel): Single<NfsShareModel> {
        return requestManager
                .doJsonRequest("/sharing/nfs/${data.id}/", Method.PUT, data,
                               NfsShareModel.SingleDeserializer())
    }

    override fun deleteNfsShare(
            data: NfsShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/sharing/nfs/${data.id}/", Method.DELETE)
    }
}