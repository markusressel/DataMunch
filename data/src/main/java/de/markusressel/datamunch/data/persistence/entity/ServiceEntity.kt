package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class ServiceEntity(
        @Id
        var entityId: Long,
        val id: Long,
        val srv_service: String,
        val srv_enabled: Boolean
)