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

import com.github.ajalt.timberkt.Timber
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(title = R.string.menu_item_status,
                                icon = MaterialDesignIconic.Icon.gmi_home, selectable = true,
                                navigationPage = NavigationPageHolder.Status)

    val Accounts = DrawerMenuItem(title = R.string.menu_item_accounts,
                                  icon = MaterialDesignIconic.Icon.gmi_account, selectable = true,
                                  navigationPage = NavigationPageHolder.Accounts)

    val Services = DrawerMenuItem(title = R.string.menu_item_services,
                                  drawableRes = R.drawable.cube_outline, selectable = true,
                                  navigationPage = NavigationPageHolder.Services)

    val Sharing = DrawerMenuItem(title = R.string.menu_item_sharing,
                                 icon = MaterialDesignIconic.Icon.gmi_folder_shared,
                                 selectable = true, navigationPage = NavigationPageHolder.Sharing)

    val Storage = DrawerMenuItem(title = R.string.menu_item_storage,
                                 icon = MaterialDesignIconic.Icon.gmi_storage, selectable = true,
                                 navigationPage = NavigationPageHolder.Storage)

    val Jails = DrawerMenuItem(title = R.string.menu_item_jails, drawableRes = R.drawable.ic_jail,
                               selectable = true, navigationPage = NavigationPageHolder.Jails)

    val Plugins = DrawerMenuItem(title = R.string.menu_item_plugins,
                                 icon = MaterialDesignIconic.Icon.gmi_puzzle_piece,
                                 selectable = true, navigationPage = NavigationPageHolder.Plugins)

    val System = DrawerMenuItem(title = R.string.menu_item_system,
                                icon = MaterialDesignIconic.Icon.gmi_settings, selectable = true,
                                navigationPage = NavigationPageHolder.System)

    val Tasks = DrawerMenuItem(title = R.string.menu_item_tasks,
                               icon = MaterialDesignIconic.Icon.gmi_assignment_check,
                               selectable = true, navigationPage = NavigationPageHolder.Tasks)

    val Settings = DrawerMenuItem(title = R.string.menu_item_settings,
                                  icon = MaterialDesignIconic.Icon.gmi_settings, selectable = false,
                                  navigationPage = NavigationPageHolder.Settings)

    val About = DrawerMenuItem(title = R.string.menu_item_about,
                               icon = MaterialDesignIconic.Icon.gmi_info, selectable = false,
                               navigationPage = NavigationPageHolder.About)

    fun fromId(drawerItemIdentifier: Long): DrawerMenuItem? {
        return when (drawerItemIdentifier) {
            Status.identifier -> Status
            Accounts.identifier -> Accounts
            Services.identifier -> Services
            Sharing.identifier -> Sharing
            Storage.identifier -> Storage
            Jails.identifier -> Jails
            Plugins.identifier -> Plugins
            System.identifier -> System
            Tasks.identifier -> Tasks
            Settings.identifier -> Settings
            About.identifier -> About
            else -> {
                Timber
                        .w { "Unknown menu item identifier: $drawerItemIdentifier" }
                null
            }
        }
    }

}