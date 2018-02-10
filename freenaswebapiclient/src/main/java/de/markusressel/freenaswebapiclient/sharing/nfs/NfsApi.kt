package de.markusressel.freenaswebapiclient.sharing.nfs

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_LIMIT
import de.markusressel.freenaswebapiclient.RequestManager.Companion.DEFAULT_OFFSET
import io.reactivex.Single

/**
 * Created by Markus on 10.02.2018.
 */
interface NfsApi {

    /**
     * Get a list of all NFS shares
     */
    fun getNfsShares(limit: Int = DEFAULT_LIMIT,
                     offset: Int = DEFAULT_OFFSET): Single<List<NfsShareModel>>

    /**
     * Create a new NFS share
     */
    fun createNfsShare(data: NfsShareModel): Single<NfsShareModel>

    /**
     * Update an existing NFS share
     */
    fun updateNfsShare(data: NfsShareModel): Single<NfsShareModel>

    /**
     * Delete an existing NFS share
     */
    fun deleteNfsShare(data: NfsShareModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}