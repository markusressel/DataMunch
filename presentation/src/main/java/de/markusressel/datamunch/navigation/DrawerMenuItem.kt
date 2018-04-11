/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.navigation

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import de.markusressel.datamunch.view.IconHandler
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by Markus on 08.01.2018.
 */
data class DrawerMenuItem(@StringRes val title: Int,
                          val icon: IIcon? = null, @DrawableRes val drawableRes: Int? = null,
                          val selectable: Boolean, val navigationPage: NavigationPage) {

    val identifier: Long = DrawerMenuItem
            .identifier
            .getAndAdd(1)

    /**
     * Get the icon for this DrawerMenuItem
     */
    fun getIcon(iconHandler: IconHandler): Drawable {
        icon
                ?.let {
                    return iconHandler
                            .getNavigationIcon(icon)
                }

        drawableRes
                ?.let {
                    val drawable = iconHandler
                            .context
                            .getDrawable(drawableRes)
                    val color = iconHandler
                            .themeHelper
                            .getThemeAttrColor(iconHandler.context, android.R.attr.textColorPrimary)
                    drawable
                            .setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

                    return drawable
                }

        return IconicsDrawable(iconHandler.context)
    }

    companion object {
        val identifier: AtomicLong = AtomicLong(1)
    }
}