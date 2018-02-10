package de.markusressel.freenaswebapiclient.sharing.cifs

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_LIMIT
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_OFFSET
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
interface CifsApi {

    /**
     * Get a list of all CIFS shares
     */
    fun getCifsShares(limit: Int = DEFAULT_LIMIT,
                      offset: Int = DEFAULT_OFFSET): Single<List<CifsShareModel>>

    /**
     * Create a new CIFS share
     */
    fun createCifsShare(data: CifsShareModel): Single<CifsShareModel>

    /**
     * Update an existing CIFS share
     */
    fun updateCifsShare(data: CifsShareModel): Single<CifsShareModel>

    /**
     * Delete an existing CIFS share
     */
    fun deleteCifsShare(data: CifsShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}