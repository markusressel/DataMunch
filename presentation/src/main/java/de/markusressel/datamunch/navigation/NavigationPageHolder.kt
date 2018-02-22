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

    val MainPage = NavigationPage(fragment = ::ServerStatusFragment, tag = "ServerStatusFragment")
    val AccountsPage = NavigationPage(fragment = ::AccountFragment, tag = "AccountFragment")
    val ServicesPage = NavigationPage(fragment = ::ServicesFragment, tag = "ServicesFragment")
    val SharingPage = NavigationPage(fragment = ::SharingFragment, tag = "SharingFragment")
    val StoragePage = NavigationPage(fragment = ::StorageFragment, tag = "StorageFragment")
    val SystemPage = NavigationPage(fragment = ::SystemFragment, tag = "SystemFragment")
    val JailsPage = NavigationPage(fragment = ::JailFragment, tag = "JailFragment")
    val PluginsPage = NavigationPage(fragment = ::PluginFragment, tag = "PluginFragment")
    val FileUploaderPage = NavigationPage(fragment = ::FileUploaderFragment,
                                          tag = "FileUploaderFragment")
    val PreferencesOverviewPage = NavigationPage(
            activityClass = PreferenceOverviewActivity::class.java)
    val AboutPage = NavigationPage()

}