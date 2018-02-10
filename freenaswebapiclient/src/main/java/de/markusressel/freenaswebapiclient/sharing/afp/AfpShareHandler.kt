package de.markusressel.freenaswebapiclient.sharing.afp

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
class AfpShareHandler(private val requestManager: RequestManager) : AfpApi {

    override fun getAfpShares(limit: Int, offset: Int): Single<List<AfpShareModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/sharing/afp/", params, Method.GET, AfpShareModel.ListDeserializer())
    }

    override fun createAfpShare(data: AfpShareModel): Single<AfpShareModel> {
        return requestManager
                .doJsonRequest("/sharing/afp/", Method.POST, data,
                               AfpShareModel.SingleDeserializer())
    }

    override fun updateAfpShare(data: AfpShareModel): Single<AfpShareModel> {
        return requestManager
                .doJsonRequest("/sharing/afp/${data.id}/", Method.PUT, data,
                               AfpShareModel.SingleDeserializer())
    }

    override fun deleteAfpShare(
            data: AfpShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/sharing/afp/${data.id}/", Method.DELETE)
    }
}