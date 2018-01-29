package de.markusressel.datamunch.data.freebsd.freenas.webapi.data

/**
 * Created by Markus on 29.01.2018.
 */
data class ServiceJSON(
        val id: Int,
        val srv_service: String,
        val srv_enabled: Boolean
)