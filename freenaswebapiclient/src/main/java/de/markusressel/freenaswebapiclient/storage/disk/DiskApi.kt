package de.markusressel.freenaswebapiclient.storage.disk

import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface DiskApi {
    /**
     * Get a list of all disks
     */
    fun getDisks(limit: Int = RequestManager.DEFAULT_LIMIT,
                 offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<DiskModel>>

    /**
     * Update a disk
     */
    fun updateDisk(data: DiskModel): Single<DiskModel>
}