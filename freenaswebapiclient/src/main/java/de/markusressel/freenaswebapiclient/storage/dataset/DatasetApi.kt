package de.markusressel.freenaswebapiclient.storage.dataset

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import io.reactivex.Single

interface DatasetApi {
    /**
     * Get a list of all datasets
     */
    fun getDatasets(): Single<List<DatasetModel>>

    /**
     * Get datasets for a volume
     */
    fun getDatasets(volumeId: Long): Single<List<DatasetModel>>

    /**
     * Create a dataset for a volume
     */
    fun createDataset(volumeId: Long, data: DatasetModel): Single<DatasetModel>

    /**
     * Delete dataset of a volume
     */
    fun deleteDataset(volumeId: Long,
                      datasetName: String): Single<Pair<Response, Result<ByteArray, FuelError>>>

}