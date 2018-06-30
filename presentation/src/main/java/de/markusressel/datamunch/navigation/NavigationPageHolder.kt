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

import de.markusressel.datamunch.view.activity.MainActivity
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.pages.*
import de.markusressel.datamunch.view.fragment.preferences.MainPreferenceFragment
import de.markusressel.datamunch.view.fragment.services.ServicesFragment

/**
 * Created by Markus on 08.01.2018.
 */
object NavigationPageHolder {

    val Main: NavigationPage = NavigationPage(activityClass = MainActivity::class.java)

    val Status: NavigationPage = NavigationPage(fragment = ::ServerStatusFragment,
                                                tag = "ServerStatusFragment")
    val Accounts: NavigationPage = NavigationPage(fragment = ::AccountPage, tag = "AccountPage")
    val Services: NavigationPage = NavigationPage(fragment = ::ServicesFragment,
                                                  tag = "ServicesFragment")
    val Sharing: NavigationPage = NavigationPage(fragment = ::SharingPage, tag = "SharingPage")
    val Storage: NavigationPage = NavigationPage(fragment = ::StoragePage, tag = "StoragePage")
    val System: NavigationPage = NavigationPage(fragment = ::SystemPage, tag = "SystemPage")
    val Jails: NavigationPage = NavigationPage(fragment = ::JailPage, tag = "JailPage")
    val Plugins: NavigationPage = NavigationPage(fragment = ::PluginPage, tag = "PluginPage")
    val Tasks: NavigationPage = NavigationPage(fragment = ::TasksPage, tag = "TasksPage")

    val Settings = NavigationPage(fragment = ::MainPreferenceFragment, tag = "Settings")
    //    val Settings = NavigationPage(activityClass = PreferenceOverviewActivity::class.java)

    val About = NavigationPage()

}