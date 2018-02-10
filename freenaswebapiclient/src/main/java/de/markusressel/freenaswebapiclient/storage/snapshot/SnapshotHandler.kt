package de.markusressel.freenaswebapiclient.storage.snapshot

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.github.salomonbrys.kotson.jsonObject
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class SnapshotHandler(private val requestManager: RequestManager) : SnapshotApi {

    override fun getSnapshots(limit: Int, offset: Int): Single<List<SnapshotModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/storage/snapshot/", params, Method.GET,
                           SnapshotModel.ListDeserializer())
    }

    override fun createSnapshot(dataset: String, name: String,
                                recursive: Boolean): Single<SnapshotModel> {
        val data = jsonObject("dataset" to dataset, "name" to name, "recursive" to recursive)
        return requestManager
                .doJsonRequest("/storage/snapshot/", Method.POST, data,
                               SnapshotModel.SingleDeserializer())
    }

    override fun deleteSnapshot(
            snapshotId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/storage/snapshot/$snapshotId/", Method.DELETE)
    }

    override fun cloneSnapshot(snapshotId: Long,
                               cloneName: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        val data = jsonObject("name" to cloneName)
        return requestManager
                .doJsonRequest("/storage/snapshot/$snapshotId/clone/", Method.POST, data)
    }

    override fun rollbackSnapshot(snapshotId: Long,
                                  force: Boolean): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        val data = jsonObject("force" to force)
        return requestManager
                .doJsonRequest("/storage/snapshot/$snapshotId/rollback/", Method.POST, data)
    }

}