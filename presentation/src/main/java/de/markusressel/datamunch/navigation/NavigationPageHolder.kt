package de.markusressel.datamunch.navigation

import de.markusressel.datamunch.navigation.page.NavigationPage
import de.markusressel.datamunch.view.activity.*
import de.markusressel.datamunch.view.activity.fileuploader.FileUploaderActivity
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity

/**
 * Created by Markus on 08.01.2018.
 */
object NavigationPageHolder {

    val MainPage = NavigationPage(MainActivity::class.java)
    val AccountsPage = NavigationPage(AccountsActivity::class.java)
    val ServicesPage = NavigationPage(ServicesActivity::class.java)
    val JailsPage = NavigationPage(JailsActivity::class.java)
    val PluginsPage = NavigationPage(PluginsActivity::class.java)
    val FileUploaderPage = NavigationPage(FileUploaderActivity::class.java)
    val PreferencesOverviewPage = NavigationPage(PreferenceOverviewActivity::class.java)
    val AboutPage = NavigationPage(null)

}