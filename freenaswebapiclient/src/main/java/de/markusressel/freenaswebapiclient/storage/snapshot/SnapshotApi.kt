package de.markusressel.freenaswebapiclient.storage.snapshot

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface SnapshotApi {
    /**
     * Get a list of all snapshots
     */
    fun getSnapshots(limit: Int = RequestManager.DEFAULT_LIMIT,
                     offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<SnapshotModel>>

    /**
     * Create a new snapshot
     *
     * @param dataset name of dataset to snapshot
     * @param name name of the snapshot
     * @param recursive True if you want it to recursively snapshot the dataset
     */
    fun createSnapshot(dataset: String, name: String,
                       recursive: Boolean = true): Single<SnapshotModel>

    /**
     * Delete a snapshot
     */
    fun deleteSnapshot(snapshotId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Delete a snapshot
     */
    fun cloneSnapshot(snapshotId: Long, cloneName: String): Single<String>

    /**
     * Rollback a snapshot
     */
    fun rollbackSnapshot(snapshotId: Long, force: Boolean): Single<String>
}