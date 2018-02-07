package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 07.02.2018.
 */
@Entity
data class UserEntity(
        @Id
        val id: Int,
        var username: String,
        var fullName: String,
        var eMail: String,
        var icon: Int
)