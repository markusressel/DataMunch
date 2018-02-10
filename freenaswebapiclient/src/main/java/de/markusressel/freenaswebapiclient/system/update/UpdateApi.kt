package de.markusressel.freenaswebapiclient.system.update

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface UpdateApi {
    /**
     * Get a list of pending system updates
     */
    fun getPendingUpdates(limit: Int = RequestManager.DEFAULT_LIMIT,
                          offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<UpdateModel>>

    /**
     * Perform system update
     */
    fun performSystemUpdate(): Single<Pair<Response, Result<ByteArray, FuelError>>>

}