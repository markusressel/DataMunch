package de.markusressel.datamunch.navigation

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import de.markusressel.datamunch.view.IconicsHelper

/**
 * Created by Markus on 08.01.2018.
 */
data class DrawerMenuItem(
        val identifier: Long,
        @StringRes
        val title: Int,
        val icon: IIcon? = null,
        @DrawableRes
        val drawableRes: Int? = null,
        val selectable: Boolean) {


    /**
     * Get the icon for this DrawerMenuItem
     */
    fun getIcon(iconicsHelper: IconicsHelper): Drawable {
        icon?.let {
            return iconicsHelper.getMenuIcon(icon)
        }

        drawableRes?.let {
            val drawable = iconicsHelper.context.getDrawable(drawableRes)
            val color = iconicsHelper.themeHelper.getThemeAttrColor(iconicsHelper.context, android.R.attr.textColorPrimary)
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

            return drawable
        }

        return IconicsDrawable(iconicsHelper.context)
    }
}