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

package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
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
import de.markusressel.datamunch.event.LocaleChangedEvent
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.event.ThemeChangedEvent
import de.markusressel.datamunch.extensions.isTablet
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerItemHolder.About
import de.markusressel.datamunch.navigation.DrawerItemHolder.Settings
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.component.LockComponent
import de.markusressel.datamunch.view.component.LockComponent.Companion.isScreenLocked
import de.markusressel.datamunch.view.fragment.preferences.MainPreferenceFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.*

/**
 * A base activity that provides the full navigation navigationDrawer implementation
 *
 * Created by Markus on 07.01.2018.
 */
abstract class NavigationDrawerActivity : DaggerSupportActivityBase() {

    override val layoutRes: Int
        get() = R.layout.activity_main

    private val lockComponent: LockComponent = LockComponent({ this }, { preferencesHolder })

    protected val navController by lazy { Navigation.findNavController(this, R.id.navHostFragment) }
    protected lateinit var navigationDrawer: Drawer

    override fun setContentView(view: View?) {
        val contentView = lockComponent
                .setContentView(view)
        super
                .setContentView(contentView)
    }

    override fun onDestroy() {
        lockComponent
                .onDestroy()
        super
                .onDestroy()
    }

    override fun onContentViewInflated(savedInstanceState: Bundle?) {
        val menuItemList = initDrawerMenuItems()
        val accountHeader = initAccountHeader()

        val builder = DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withDrawerItems(menuItemList)
                .withCloseOnClick(false)
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)

        if (isTablet()) {
            navigationDrawer = builder
                    .buildView()

            drawerLayoutParent
                    .visibility = View
                    .VISIBLE
            drawerDividerView
                    .visibility = View
                    .VISIBLE

            drawerLayoutParent
                    .addView(navigationDrawer.slider, 0)
        } else {
            drawerLayoutParent
                    .visibility = View
                    .GONE
            drawerDividerView
                    .visibility = View
                    .GONE

            navigationDrawer = builder
                    .build()
        }

        navigationDrawer

        val appBarConfiguration = AppBarConfiguration(
                navGraph = navController.graph,
                drawerLayout = navigationDrawer.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super
                .onStart()

        Bus
                .observe<LockEvent>()
                .subscribe {
                    setDrawerLockState(it.lock)
                }
                .registerInBus(this)

        Bus
                .observe<LocaleChangedEvent>()
                .subscribe {
                    restartActivity()
                    recreate()
                }
                .registerInBus(this)

        Bus
                .observe<ThemeChangedEvent>()
                .subscribe {
                    restartActivity()
                }
                .registerInBus(this)
    }

    private fun restartActivity() {
        // TODO: replace this with navController stuff
//        navigator
//                .startActivity(this, Navigator.NavigationPages.Main,
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//
//        navigator
//                .navigateTo(DrawerItemHolder.Settings)
    }

    override fun onResume() {
        super
                .onResume()

        setDrawerLockState(isScreenLocked)
    }

    private fun setDrawerLockState(locked: Boolean) {
        if (isTablet()) {
            // nothing to do here
            return
        }

        val drawerLockMode = when (locked) {
            true -> DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            false -> DrawerLayout.LOCK_MODE_UNLOCKED
        }

        navigationDrawer.drawerLayout.setDrawerLockMode(drawerLockMode)
    }

    private fun initAccountHeader(): AccountHeader {
        val profiles: MutableList<IProfile<*>> = getProfiles()

        return AccountHeaderBuilder()
                .withActivity(this)
                .withProfiles(profiles)
                .withCloseDrawerOnProfileListClick(false)
                .withCurrentProfileHiddenInList(true)
                .withDividerBelowHeader(true)
                .withOnAccountHeaderListener { _, profile, current ->
                    Timber
                            .d { "Pressed profile: '$profile' with current: '$current'" }
                    false
                }

                .build()
    }

    private fun getProfiles(): MutableList<IProfile<*>> {
        val profiles: MutableList<IProfile<*>> = LinkedList()

        // TODO: implement different servers as profiles

        profiles
                .add(ProfileDrawerItem().withName("Markus Ressel").withEmail(
                        "mail@markusressel.de").withIcon(R.mipmap.ic_launcher))

        profiles
                .add(ProfileDrawerItem().withName("Max Mustermann").withEmail("").withIcon(
                        R.mipmap.ic_launcher))

        return profiles
    }

    private fun initDrawerMenuItems(): MutableList<IDrawerItem<*, *>> {
        val menuItemList: MutableList<IDrawerItem<*, *>> = LinkedList()

        val clickListener = Drawer
                .OnDrawerItemClickListener { _, _, drawerItem ->
                    val drawerMenuItem = DrawerItemHolder
                            .fromId(drawerItem.identifier.toInt())

                    drawerMenuItem?.let {
                        navController.navigate(it.id)

                        if (drawerItem.isSelectable) {
                            // set new title
                            setTitle(drawerMenuItem.title)
                        }

                        if (!isTablet()) {
                            navigationDrawer.closeDrawer()
                        }
                        return@OnDrawerItemClickListener true
                    }

                    false
                }



        listOf(DrawerItemHolder.Status,
                DrawerItemHolder.Accounts,
                DrawerItemHolder.Storage,
                DrawerItemHolder.Sharing,
                DrawerItemHolder.Services,
                DrawerItemHolder.Plugins,
                DrawerItemHolder.Jails,
                DrawerItemHolder.System,
                DrawerItemHolder.Tasks)
                .forEach {
                    menuItemList
                            .add(createPrimaryMenuItem(it, clickListener))
                }

        menuItemList
                .add(DividerDrawerItem())

        menuItemList
                .add(createPrimaryMenuItem(Settings, clickListener))

        menuItemList
                .add(createSecondaryMenuItem(About, clickListener))

        return menuItemList
    }

    private fun createPrimaryMenuItem(menuItem: DrawerMenuItem,
                                      clickListener: Drawer.OnDrawerItemClickListener): PrimaryDrawerItem {
        return PrimaryDrawerItem()
                .withName(menuItem.title)
                .withIdentifier(menuItem.id.toLong())
                .withIcon(menuItem.getIcon(iconHandler))
                .withSelectable(menuItem.selectable)
                .withOnDrawerItemClickListener(clickListener)
    }

    private fun createSecondaryMenuItem(menuItem: DrawerMenuItem,
                                        clickListener: Drawer.OnDrawerItemClickListener): SecondaryDrawerItem {
        return SecondaryDrawerItem()
                .withName(menuItem.title)
                .withIdentifier(menuItem.id.toLong())
                .withIcon(menuItem.getIcon(iconHandler))
                .withSelectable(menuItem.selectable)
                .withOnDrawerItemClickListener(clickListener)
    }

    override fun onBackPressed() {
        if (navigationDrawer.isDrawerOpen) {
            navigationDrawer.closeDrawer()
            return
        }

        // pass onBack event to fragments
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val currentlyVisibleFragment = navHost?.childFragmentManager?.primaryNavigationFragment
        when (currentlyVisibleFragment) {
            is MainPreferenceFragment -> {
                if (currentlyVisibleFragment.onBackPressed()) {
                    return
                }
            }
        }

        navController.navigateUp()
        super.onBackPressed()

        // TODO: update drawer selection on back press
//        if (previousPage != null) {
//            navigationDrawer.setSelection(previousPage.drawerMenuItem.identifier, false)
//            return
//        }
    }
}