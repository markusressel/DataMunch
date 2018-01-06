package de.markusressel.datamunch.model

import android.support.annotation.DrawableRes

/**
 * Created by Markus on 06.01.2018.
 */
data class UserModel(
        val id: String,
        var username: String,
        var fullName: String,
        var eMail: String,
        @DrawableRes
        var icon: Int
)