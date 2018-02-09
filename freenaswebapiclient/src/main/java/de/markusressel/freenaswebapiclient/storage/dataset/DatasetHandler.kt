package de.markusressel.freenaswebapiclient.storage.dataset

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class DatasetHandler(private val requestManager: RequestManager) : DatasetApi {

    override fun getDatasets(): Single<List<DatasetModel>> {
        return requestManager
                .doRequest("/storage/dataset/", Method.GET, DatasetModel.ListDeserializer())
    }

    override fun getDatasets(volumeId: Long): Single<List<DatasetModel>> {
        return requestManager
                .doRequest("/storage/volume/$volumeId/datasets/", Method.GET,
                           DatasetModel.ListDeserializer())
    }

    override fun createDataset(volumeId: Long, data: DatasetModel): Single<DatasetModel> {
        return requestManager
                .doJsonRequest("/storage/volume/$volumeId/datasets/", Method.POST, data,
                               DatasetModel.SingleDeserializer())
    }

    override fun deleteDataset(volumeId: Long,
                               datasetName: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/storage/volume/$volumeId/datasets/$datasetName", Method.DELETE)
    }

}