package de.markusressel.freenaswebapiclient.jails.jail

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.storage.volume.VolumeModel
import io.reactivex.Single

interface VolumeApi {
    /**
     * Get a list of all Volumes
     */
    fun getVolumes(limit: Int = RequestManager.DEFAULT_LIMIT,
                   offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<VolumeModel>>

    /**
     * Create a Volume
     */
    fun createVolume(volumeName: String): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Delete a Volume
     */
    fun deleteVolume(volumeId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

}