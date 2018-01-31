package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 31.01.2018.
 */
@Entity
data class AuthenticationEntity(
        @Id
        var id: Long,
        val username: String,
        val password: String
)