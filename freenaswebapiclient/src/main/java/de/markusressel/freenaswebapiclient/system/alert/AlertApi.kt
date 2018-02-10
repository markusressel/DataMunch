package de.markusressel.freenaswebapiclient.system.alert

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface AlertApi {
    /**
     * Get a list of system alerts
     */
    fun getSystemAlerts(limit: Int = RequestManager.DEFAULT_LIMIT,
                        offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<AlertModel>>

    /**
     * Dismiss a system alert
     */
    fun dismissSystemAlert(alert: AlertModel): Single<Pair<Response, Result<ByteArray, FuelError>>>

}