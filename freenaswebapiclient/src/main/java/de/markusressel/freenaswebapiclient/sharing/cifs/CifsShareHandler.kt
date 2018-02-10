package de.markusressel.freenaswebapiclient.sharing.cifs

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
class CifsShareHandler(private val requestManager: RequestManager) : CifsApi {

    override fun getCifsShares(limit: Int, offset: Int): Single<List<CifsShareModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/sharing/cifs/", params, Method.GET, CifsShareModel.ListDeserializer())
    }

    override fun createCifsShare(data: CifsShareModel): Single<CifsShareModel> {
        return requestManager
                .doJsonRequest("/sharing/cifs/", Method.POST, data,
                               CifsShareModel.SingleDeserializer())
    }

    override fun updateCifsShare(data: CifsShareModel): Single<CifsShareModel> {
        return requestManager
                .doJsonRequest("/sharing/cifs/${data.id}/", Method.PUT, data,
                               CifsShareModel.SingleDeserializer())
    }

    override fun deleteCifsShare(
            data: CifsShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/sharing/cifs/${data.id}/", Method.DELETE)
    }
}