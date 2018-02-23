package de.markusressel.datamunch.navigation

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import de.markusressel.datamunch.navigation.page.NavigationPage
import de.markusressel.datamunch.view.IconHandler

/**
 * Created by Markus on 08.01.2018.
 */
data class DrawerMenuItem(val identifier: Long, @StringRes val title: Int,
                          val icon: IIcon? = null, @DrawableRes val drawableRes: Int? = null,
                          val selectable: Boolean, val navigationPage: NavigationPage) {


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
}