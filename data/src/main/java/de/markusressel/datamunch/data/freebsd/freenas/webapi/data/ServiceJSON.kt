package de.markusressel.datamunch.data.freebsd.freenas.webapi.data

import de.markusressel.datamunch.data.persistence.entity.ServiceEntity

/**
 * Created by Markus on 29.01.2018.
 */
data class ServiceJSON(
        val id: Long,
        val srv_service: String,
        val srv_enabled: Boolean
) {
    /**
     * Get an newEntity for this class
     */
    fun newEntity(): ServiceEntity {
        return ServiceEntity(
                0,
                this.id,
                this.srv_service,
                this.srv_enabled
        )
    }
}