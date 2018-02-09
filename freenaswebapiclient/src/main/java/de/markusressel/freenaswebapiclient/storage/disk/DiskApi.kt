package de.markusressel.freenaswebapiclient.storage.disk

import io.reactivex.Single

interface DiskApi {
    /**
     * Get a list of all disks
     */
    fun getDisks(): Single<List<DiskModel>>

    /**
     * Update a disk
     */
    fun updateDisk(data: DiskModel): Single<DiskModel>
}