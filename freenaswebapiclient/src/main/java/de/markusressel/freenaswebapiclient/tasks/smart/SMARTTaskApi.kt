package de.markusressel.freenaswebapiclient.tasks.smart

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 23.02.2018.
 */
interface SMARTTaskApi {

    /**
     * Get a list of all S.M.A.R.T. tasks
     */
    fun getSMARTTasks(limit: Int = RequestManager.DEFAULT_LIMIT,
                      offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<SMARTTaskModel>>

    /**
     * Create a new S.M.A.R.T. task
     */
    fun createSMARTTask(data: SMARTTaskModel): Single<SMARTTaskModel>

    /**
     * Update an existing S.M.A.R.T. task
     */
    fun updateSMARTTask(data: SMARTTaskModel): Single<SMARTTaskModel>

    /**
     * Delete an existing S.M.A.R.T. task
     */
    fun deleteSMARTTask(data: SMARTTaskModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}