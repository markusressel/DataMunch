package de.markusressel.freenaswebapiclient.jails.mountpoint

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface MountpointApi {
    /**
     * Get a list of all jail mountpoints
     */
    fun getMountpoints(limit: Int = RequestManager.DEFAULT_LIMIT,
                       offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<MountpointModel>>

    /**
     * Create a new mountpoint
     */
    fun createMountpoint(data: MountpointModel): Single<MountpointModel>

    /**
     * Update an existing mountpoint
     */
    fun updateMountpoint(data: MountpointModel): Single<MountpointModel>

    /**
     * Delete a mountpoint
     */
    fun deleteMountpoint(
            mountpoint: MountpointModel): Single<Pair<Response, Result<ByteArray, FuelError>>>
}