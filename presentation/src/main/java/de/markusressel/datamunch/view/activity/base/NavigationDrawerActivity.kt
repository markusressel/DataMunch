package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder.About
import de.markusressel.datamunch.navigation.DrawerItemHolder.FileUploader
import de.markusressel.datamunch.navigation.DrawerItemHolder.Jails
import de.markusressel.datamunch.navigation.DrawerItemHolder.Plugins
import de.markusressel.datamunch.navigation.DrawerItemHolder.Services
import de.markusressel.datamunch.navigation.DrawerItemHolder.Settings
import de.markusressel.datamunch.navigation.DrawerItemHolder.Status
import de.markusressel.datamunch.navigation.Navigator
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.*

/**
 * A base activity that provides the full navigation navigationDrawer implementation
 *
 * Created by Markus on 07.01.2018.
 */
abstract class NavigationDrawerActivity : DaggerSupportActivityBase() {

    protected lateinit var navigationDrawer: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuItemList = initDrawerMenuItems()
        val accountHeader = initAccountHeader()

        navigationDrawer = DrawerBuilder().withActivity(this)
                .withAccountHeader(accountHeader)
                .withDrawerItems(menuItemList)
                .withCloseOnClick(false)
                .withToolbar(toolbar)
                .withSelectedItem(getInitialNavigationDrawerSelection())
                .build()
    }

    /**
     * Override this and define an identifier which should be selected when
     * this activity is first created
     */
    abstract fun getInitialNavigationDrawerSelection(): Long

    private fun initAccountHeader(): AccountHeader {
        val profiles: MutableList<IProfile<*>> = getProfiles()

        return AccountHeaderBuilder()
                .withActivity(this)
                .withProfiles(profiles)
                .withCloseDrawerOnProfileListClick(false)
                .withCurrentProfileHiddenInList(true)
                .withDividerBelowHeader(true)
                .withOnAccountHeaderListener { view, profile, current ->
                    Timber.d { "Pressed profile: '$profile' with current: '$current'" }
                    false
                }

                .build()
    }

    private fun getProfiles(): MutableList<IProfile<*>> {
        val profiles: MutableList<IProfile<*>> = LinkedList()

        profiles.add(
                ProfileDrawerItem()
                        .withName("Markus Ressel")
                        .withEmail("")
                        .withIcon(R.mipmap.ic_launcher)
        )

        profiles.add(
                ProfileDrawerItem()
                        .withName("Iris Haderer")
                        .withEmail("")
                        .withIcon(R.mipmap.ic_launcher)
        )

        return profiles
    }

    private fun initDrawerMenuItems(): MutableList<IDrawerItem<*, *>> {
        val menuItemList: MutableList<IDrawerItem<*, *>> = LinkedList()

        val clickListener = Drawer.OnDrawerItemClickListener { view, position, drawerItem ->

            if (drawerItem.identifier == getInitialNavigationDrawerSelection()) {
                Timber.d { "Closing navigationDrawer because the clicked item (${drawerItem.identifier}) is the currently active page" }
                navigationDrawer.closeDrawer()
                return@OnDrawerItemClickListener true
            }

            when (drawerItem.identifier) {
                Status.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.MainPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                Services.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.ServicesPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                Jails.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.JailsPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                Plugins.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.PluginsPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                FileUploader.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.FileUploaderPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                Settings.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.PreferencesOverviewPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                About.identifier -> {
                    navigator.navigateTo(this, Navigator.NavigationPages.AboutPage)
                    navigationDrawer.closeDrawer()
                    true
                }

                else -> {
                    Timber.w { "Unknown menu item identifier: ${drawerItem.identifier}" }
                    false
                }
            }

        }

        for (menuItem in listOf(Status, Services, Jails, Plugins, FileUploader)) {
            menuItemList.add(
                    PrimaryDrawerItem()
                            .withName(menuItem.title)
                            .withIdentifier(menuItem.identifier)
                            .withIcon(menuItem.getIcon(iconicsHelper))
                            .withSelectable(menuItem.selectable)
                            .withOnDrawerItemClickListener(clickListener)
            )
        }

        menuItemList.add(
                DividerDrawerItem()
        )

        var menuItem = Settings
        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(menuItem.title)
                        .withIdentifier(menuItem.identifier)
                        .withIcon(menuItem.getIcon(iconicsHelper))
                        .withSelectable(menuItem.selectable)
                        .withOnDrawerItemClickListener(clickListener)
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItem = About
        menuItemList.add(
                SecondaryDrawerItem()
                        .withName(menuItem.title)
                        .withIdentifier(menuItem.identifier)
                        .withIcon(menuItem.getIcon(iconicsHelper))
                        .withSelectable(menuItem.selectable)
                        .withOnDrawerItemClickListener(clickListener)
        )

        return menuItemList
    }

    override fun onResume() {
        super.onResume()

        // reset to this page
        navigationDrawer.setSelection(getInitialNavigationDrawerSelection())
    }

    override fun onBackPressed() {
        if (navigationDrawer.isDrawerOpen) {
            navigationDrawer.closeDrawer()
            return
        }

        super.onBackPressed()
    }
}