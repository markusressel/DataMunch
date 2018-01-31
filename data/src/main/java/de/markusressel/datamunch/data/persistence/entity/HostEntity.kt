package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 31.01.2018.
 */
@Entity
data class HostEntity(
        @Id
        var id: Long,
        val hostname: String,
        val isActive: Boolean
)