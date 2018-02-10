package de.markusressel.freenaswebapiclient.sharing.afp

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_LIMIT
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_OFFSET
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
interface AfpApi {

    /**
     * Get a list of all AFP shares
     */
    fun getAfpShares(limit: Int = DEFAULT_LIMIT,
                     offset: Int = DEFAULT_OFFSET): Single<List<AfpShareModel>>

    /**
     * Create a new AFP share
     */
    fun createAfpShare(data: AfpShareModel): Single<AfpShareModel>

    /**
     * Update an existing AFP share
     */
    fun updateAfpShare(data: AfpShareModel): Single<AfpShareModel>

    /**
     * Delete an existing AFP share
     */
    fun deleteAfpShare(data: AfpShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}