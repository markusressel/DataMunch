package de.markusressel.datamunch.navigation

import android.support.annotation.StringRes
import com.mikepenz.iconics.typeface.IIcon

/**
 * Created by Markus on 08.01.2018.
 */
data class DrawerMenuItem(
        val identifier: Long,
        @StringRes
        val title: Int,
        val icon: IIcon,
        val selectable: Boolean)