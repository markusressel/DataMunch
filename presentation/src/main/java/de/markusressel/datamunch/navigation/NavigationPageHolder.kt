package de.markusressel.datamunch.navigation

import de.markusressel.datamunch.navigation.page.NavigationPage
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.ServicesFragment
import de.markusressel.datamunch.view.fragment.pages.*

/**
 * Created by Markus on 08.01.2018.
 */
object NavigationPageHolder {

    val MainPage = NavigationPage(fragment = ::ServerStatusFragment)
    val AccountsPage = NavigationPage(fragment = ::AccountFragment)
    val ServicesPage = NavigationPage(fragment = ::ServicesFragment)
    val SharingPage = NavigationPage(fragment = ::SharingFragment)
    val StoragePage = NavigationPage(fragment = ::StorageFragment)
    val SystemPage = NavigationPage(fragment = ::SystemFragment)
    val JailsPage = NavigationPage(fragment = ::JailFragment)
    val PluginsPage = NavigationPage(fragment = ::PluginFragment)
    val FileUploaderPage = NavigationPage(fragment = ::FileUploaderFragment)
    val PreferencesOverviewPage = NavigationPage(
            activityClass = PreferenceOverviewActivity::class.java)
    val AboutPage = NavigationPage()

}