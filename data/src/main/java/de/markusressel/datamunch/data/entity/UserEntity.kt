package de.markusressel.datamunch.data.entity

import android.support.annotation.DrawableRes

/**
 * User Entity used in the data layer.
 *
 * Created by Markus on 06.01.2018.
 */
data class UserEntity(
        val id: Int,
        var username: String,
        var fullName: String,
        var eMail: String,
        @DrawableRes
        var icon: Int
)