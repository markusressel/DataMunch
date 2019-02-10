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
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(id = R.id.statusPage,
            title = R.string.menu_item_status,
            icon = MaterialDesignIconic.Icon.gmi_home,
            selectable = true)

    val Accounts = DrawerMenuItem(
            id = R.id.nav_graph_account,
            title = R.string.menu_item_accounts,
            icon = MaterialDesignIconic.Icon.gmi_account,
            selectable = true)

    val Services = DrawerMenuItem(
            id = R.id.nav_graph_services,
            title = R.string.menu_item_services,
            drawableRes = R.drawable.cube_outline,
            selectable = true)

    val Sharing = DrawerMenuItem(
            id = R.id.nav_graph_sharing,
            title = R.string.menu_item_sharing,
            icon = MaterialDesignIconic.Icon.gmi_folder_shared,
            selectable = true)

    val Storage = DrawerMenuItem(
            id = R.id.nav_graph_storage,
            title = R.string.menu_item_storage,
            icon = MaterialDesignIconic.Icon.gmi_storage,
            selectable = true)

    val Jails = DrawerMenuItem(
            id = R.id.nav_graph_jail,
            title = R.string.menu_item_jails,
            drawableRes = R.drawable.ic_jail,
            selectable = true)

    val Plugins = DrawerMenuItem(
            id = R.id.nav_graph_plugins,
            title = R.string.menu_item_plugins,
            icon = MaterialDesignIconic.Icon.gmi_puzzle_piece,
            selectable = true)

    val System = DrawerMenuItem(
            id = R.id.nav_graph_system,
            title = R.string.menu_item_system,
            icon = MaterialDesignIconic.Icon.gmi_settings,
            selectable = true)

    val Tasks = DrawerMenuItem(
            id = R.id.nav_graph_tasks,
            title = R.string.menu_item_tasks,
            icon = MaterialDesignIconic.Icon.gmi_assignment_check,
            selectable = true)

    val Settings = DrawerMenuItem(
            id = R.id.preferencesPage,
            title = R.string.menu_item_settings,
            icon = MaterialDesignIconic.Icon.gmi_settings,
            selectable = false)

    val About = DrawerMenuItem(
            id = R.id.aboutPage,
            title = R.string.menu_item_about,
            icon = MaterialDesignIconic.Icon.gmi_info,
            selectable = false)

    fun fromId(drawerItemIdentifier: Int): DrawerMenuItem? {
        return when (drawerItemIdentifier) {
            Status.id -> Status
            Accounts.id -> Accounts
            Services.id -> Services
            Sharing.id -> Sharing
            Storage.id -> Storage
            Jails.id -> Jails
            Plugins.id -> Plugins
            System.id -> System
            Tasks.id -> Tasks
            Settings.id -> Settings
            About.id -> About
            else -> {
                Timber
                        .w { "Unknown menu item identifier: $drawerItemIdentifier" }
                null
            }
        }
    }

}