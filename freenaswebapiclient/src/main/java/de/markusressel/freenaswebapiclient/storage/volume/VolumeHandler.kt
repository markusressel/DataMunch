package de.markusressel.freenaswebapiclient.storage.volume

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.jail.VolumeApi
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class VolumeHandler(private val requestManager: RequestManager) : VolumeApi {

    override fun getVolumes(limit: Int, offset: Int): Single<List<VolumeModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/storage/volume/", params, Method.GET, VolumeModel.ListDeserializer())
    }

    override fun createVolume(
            volumeName: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        throw NotImplementedError()
    }

    override fun deleteVolume(
            volumeId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/storage/volume/$volumeId/start/", Method.DELETE)
    }

}