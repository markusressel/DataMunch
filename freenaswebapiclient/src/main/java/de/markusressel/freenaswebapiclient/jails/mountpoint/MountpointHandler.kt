package de.markusressel.freenaswebapiclient.jails.mountpoint

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class MountpointHandler(private val requestManager: RequestManager) : MountpointApi {

    /**
     * Get a list of all jail mountpoints
     */
    override fun getMountpoints(limit: Int, offset: Int): Single<List<MountpointModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/jails/mountpoints/", params, Method.GET,
                           MountpointModel.ListDeserializer())
    }

    /**
     * Create a new mountpoint
     */
    override fun createMountpoint(data: MountpointModel): Single<MountpointModel> {
        return requestManager
                .doJsonRequest("/jails/mountpoints/", Method.POST, data,
                               MountpointModel.SingleDeserializer())
    }

    /**
     * Update an existing mountpoint
     */
    override fun updateMountpoint(data: MountpointModel): Single<MountpointModel> {
        return requestManager
                .doJsonRequest("/jails/mountpoints/${data.id}/", Method.PUT, data,
                               MountpointModel.SingleDeserializer())
    }

    /**
     * Delete a mountpoint
     */
    override fun deleteMountpoint(
            mountpoint: MountpointModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/jails/mountpoints/${mountpoint.id}/", Method.DELETE)
    }

}