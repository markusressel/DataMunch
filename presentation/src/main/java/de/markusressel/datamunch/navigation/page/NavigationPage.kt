package de.markusressel.datamunch.navigation.page

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.github.ajalt.timberkt.Timber
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.markusressel.datamunch.navigation.DrawerItemHolder.About
import de.markusressel.datamunch.navigation.DrawerItemHolder.Accounts
import de.markusressel.datamunch.navigation.DrawerItemHolder.FileUploader
import de.markusressel.datamunch.navigation.DrawerItemHolder.Jails
import de.markusressel.datamunch.navigation.DrawerItemHolder.Plugins
import de.markusressel.datamunch.navigation.DrawerItemHolder.Services
import de.markusressel.datamunch.navigation.DrawerItemHolder.Settings
import de.markusressel.datamunch.navigation.DrawerItemHolder.Sharing
import de.markusressel.datamunch.navigation.DrawerItemHolder.Status
import de.markusressel.datamunch.navigation.DrawerItemHolder.Storage
import de.markusressel.datamunch.navigation.Navigator

/**
 * Created by Markus on 08.01.2018.
 */
class NavigationPage(val activityClass: Class<*>? = null, val fragment: (() -> Fragment)? = null) {

    fun createIntent(context: Context): Intent {
        return Intent(context, activityClass)
    }

    companion object {
        fun fromDrawerItem(
                drawerMenuItem: IDrawerItem<Any, RecyclerView.ViewHolder>): NavigationPage? {
            return when (drawerMenuItem.identifier) {
                Status.identifier -> Navigator.NavigationPages.MainPage
                Accounts.identifier -> Navigator.NavigationPages.AccountsPage
                Services.identifier -> Navigator.NavigationPages.ServicesPage
                Sharing.identifier -> Navigator.NavigationPages.SharingPage
                Storage.identifier -> Navigator.NavigationPages.StoragePage
                Jails.identifier -> Navigator.NavigationPages.JailsPage
                Plugins.identifier -> Navigator.NavigationPages.PluginsPage
                Navigator.DrawerItems.System.identifier -> Navigator.NavigationPages.SystemPage
                FileUploader.identifier -> Navigator.NavigationPages.FileUploaderPage
                Settings.identifier -> Navigator.NavigationPages.PreferencesOverviewPage
                About.identifier -> Navigator.NavigationPages.AboutPage
                else -> {
                    Timber
                            .w { "Unknown menu item identifier: ${drawerMenuItem.identifier}" }
                    null
                }
            }
        }
    }

}