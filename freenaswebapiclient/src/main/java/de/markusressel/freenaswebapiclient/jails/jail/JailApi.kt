package de.markusressel.freenaswebapiclient.jails.jail

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface JailApi {
    /**
     * Get a list of all jails
     */
    fun getJails(limit: Int = RequestManager.DEFAULT_LIMIT,
                 offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<JailModel>>

    /**
     * Create a jail
     */
    fun createJail(data: JailModel): Single<JailModel>

    /**
     * Start a jail
     */
    fun startJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Stop a jail
     */
    fun stopJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Restart a jail
     */
    fun restartJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * Delete a jail
     */
    fun deleteJail(jailId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>
}