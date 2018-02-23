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

    val Main: NavigationPage = NavigationPage(fragment = ::ServerStatusFragment,
                                              tag = "ServerStatusFragment")
    val Accounts: NavigationPage = NavigationPage(fragment = ::AccountPage, tag = "AccountPage")
    val Services: NavigationPage = NavigationPage(fragment = ::ServicesFragment,
                                                  tag = "ServicesFragment")
    val Sharing: NavigationPage = NavigationPage(fragment = ::SharingPage, tag = "SharingPage")
    val Storage: NavigationPage = NavigationPage(fragment = ::StoragePage, tag = "StoragePage")
    val System: NavigationPage = NavigationPage(fragment = ::SystemPage, tag = "SystemPage")
    val Jails: NavigationPage = NavigationPage(fragment = ::JailPage, tag = "JailPage")
    val Plugins: NavigationPage = NavigationPage(fragment = ::PluginPage, tag = "PluginPage")
    val FileUploader: NavigationPage = NavigationPage(fragment = ::FileUploaderPage,
                                                      tag = "FileUploaderPage")
    val Tasks: NavigationPage = NavigationPage(fragment = ::TasksPage, tag = "TasksPage")
    val Settings = NavigationPage(activityClass = PreferenceOverviewActivity::class.java)
    val About = NavigationPage()

}