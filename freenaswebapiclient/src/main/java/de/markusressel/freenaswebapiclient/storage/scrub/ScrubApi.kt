package de.markusressel.freenaswebapiclient.storage.scrub

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 22.02.2018.
 */
interface ScrubApi {

    /**
     * Get a list of all scrubs
     */
    fun getScrubs(limit: Int = RequestManager.DEFAULT_LIMIT,
                  offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<ScrubModel>>

    /**
     * Create a new scrub
     */
    fun createScrub(data: ScrubModel): Single<ScrubModel>

    /**
     * Update an existing scrub
     */
    fun updateScrub(data: ScrubModel): Single<ScrubModel>

    /**
     * Delete an existing scrub
     */
    fun deleteScrub(data: ScrubModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}