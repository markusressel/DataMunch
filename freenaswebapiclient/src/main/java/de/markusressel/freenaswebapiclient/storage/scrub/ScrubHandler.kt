package de.markusressel.freenaswebapiclient.storage.scrub

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class ScrubHandler(private val requestManager: RequestManager) : ScrubApi {

    override fun getScrubs(limit: Int, offset: Int): Single<List<ScrubModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/storage/scrub/", params, Method.GET, ScrubModel.ListDeserializer())
    }

    override fun createScrub(data: ScrubModel): Single<ScrubModel> {
        return requestManager
                .doJsonRequest("/storage/scrub/", Method.POST, data,
                               ScrubModel.SingleDeserializer())
    }

    override fun updateScrub(data: ScrubModel): Single<ScrubModel> {
        return requestManager
                .doJsonRequest("/storage/scrub/${data.id}/", Method.PUT, data,
                               ScrubModel.SingleDeserializer())
    }

    override fun deleteScrub(
            data: ScrubModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/storage/scrub/${data.id}/", Method.DELETE)
    }

}