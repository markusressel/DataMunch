package de.markusressel.freenaswebapiclient.system

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.system.alert.AlertApi
import de.markusressel.freenaswebapiclient.system.update.UpdateApi
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
interface SystemApi : AlertApi, UpdateApi {

    /**
     * Get the version of the currently installed software
     */
    fun getSoftwareVersion(): Single<SoftwareVersionModel>

    /**
     * !!!DANGER ZONE!!!
     *
     * Reboot the system
     */
    fun rebootSystem(): Single<Pair<Response, Result<ByteArray, FuelError>>>

    /**
     * !!!DANGER ZONE!!!
     *
     * Shutdown the system
     */
    fun shutdownSystem(): Single<Pair<Response, Result<ByteArray, FuelError>>>

}