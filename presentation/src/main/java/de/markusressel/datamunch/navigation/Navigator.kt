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

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.util.Colors
import com.mikepenz.materialdrawer.Drawer
import de.markusressel.datamunch.R
import de.markusressel.datamunch.preferences.KutePreferencesHolder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 07.01.2018.
 */
@Singleton
class Navigator @Inject constructor(
        private val preferencesHolder: KutePreferencesHolder

) {

    lateinit var activity: AppCompatActivity
    lateinit var drawer: Drawer

    private fun navigateToAbout(activityContext: Context) {
        val themeVal = preferencesHolder
                .theme
                .persistedValue

        val aboutLibTheme: Libs.ActivityStyle
        aboutLibTheme = if (themeVal == activityContext.getString(R.string.theme_light_value)) {
            Libs
                    .ActivityStyle
                    .LIGHT_DARK_TOOLBAR
        } else {
            Libs
                    .ActivityStyle
                    .DARK
        }

        LibsBuilder()
                .withActivityStyle(aboutLibTheme)
                .withActivityColor(
                        Colors(ContextCompat.getColor(activityContext, R.color.primaryColor),
                                ContextCompat.getColor(activityContext, R.color.primaryColor_dark)))
                .withActivityTitle(activityContext.getString(R.string.menu_item_about))
                .start(activityContext)
    }

    companion object {
        val DrawerItems = DrawerItemHolder
    }

}