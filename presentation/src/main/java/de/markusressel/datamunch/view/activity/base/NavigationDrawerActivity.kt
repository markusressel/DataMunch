package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
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
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.extensions.isTablet
import de.markusressel.datamunch.navigation.DrawerItemHolder
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
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.navigation.Navigator
import de.markusressel.datamunch.navigation.page.NavigationPage
import de.markusressel.datamunch.view.plugin.LockPlugin.Companion.isLocked
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

    private lateinit var navigationDrawer: Drawer

    private var currentDrawerItemIdentifier by savedInstanceState(
            DrawerItemHolder.Status.identifier)
    private var lastFragmentTag: String? by savedInstanceState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        val menuItemList = initDrawerMenuItems()
        val accountHeader = initAccountHeader()

        val builder = DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withDrawerItems(menuItemList)
                .withCloseOnClick(false)
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(currentDrawerItemIdentifier)

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
                .setSelection(currentDrawerItemIdentifier, false)

        DrawerItemHolder
                .fromId(currentDrawerItemIdentifier)
                ?.let {
                    setTitle(it.title)
                }

        if (savedInstanceState == null) {
            NavigationPage
                    .fromDrawerItem(currentDrawerItemIdentifier)
                    ?.let {
                        lastFragmentTag = navigator
                                .navigateTo(this, it, lastFragmentTag)
                    }
        }
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
    }

    override fun onResume() {
        super
                .onResume()

        setDrawerLockState(isLocked)
    }

    private fun setDrawerLockState(locked: Boolean) {
        if (isTablet()) {
            // nothing to do here
            return
        }

        if (locked) {
            navigationDrawer
                    .drawerLayout
                    .setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            navigationDrawer
                    .drawerLayout
                    .setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    /**
     * Override this and define an identifier which should be selected when
     * this activity is first created
     */
    abstract fun getDrawerMenuItem(): DrawerMenuItem

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
                .add(ProfileDrawerItem().withName("Iris Haderer").withEmail("").withIcon(
                        R.mipmap.ic_launcher))

        return profiles
    }

    private fun initDrawerMenuItems(): MutableList<IDrawerItem<*, *>> {
        val menuItemList: MutableList<IDrawerItem<*, *>> = LinkedList()

        val clickListener = Drawer
                .OnDrawerItemClickListener { _, _, drawerItem ->

                    if (drawerItem.identifier == currentDrawerItemIdentifier) {
                        Timber
                                .d { "Closing navigationDrawer because the clicked item (${drawerItem.identifier}) is the currently active page" }
                        if (!isTablet()) {

                            navigationDrawer
                                    .closeDrawer()
                        }
                        return@OnDrawerItemClickListener true
                    }

                    val page: NavigationPage? = NavigationPage
                            .fromDrawerItem(drawerItem.identifier)

                    page
                            ?.let {
                                if (it.fragment != null) {
                                    lastFragmentTag = navigator
                                            .navigateTo(this, it, lastFragmentTag)
                                } else {
                                    navigator
                                            .startActivity(this, it)
                                }

                                if (drawerItem.isSelectable) {
                                    currentDrawerItemIdentifier = drawerItem
                                            .identifier

                                    // set new title
                                    val drawerMenuItem = DrawerItemHolder
                                            .fromId(drawerItem.identifier)
                                    drawerMenuItem
                                            ?.let {
                                                setTitle(it.title)
                                            }
                                }

                                if (!isTablet()) {
                                    navigationDrawer
                                            .closeDrawer()
                                }
                                return@OnDrawerItemClickListener true
                            }

                    false
                }



        for (menuItem in listOf(Status, Accounts, Storage, Sharing, Services, Plugins, Jails,
                                Navigator.DrawerItems.System)) {
            menuItemList
                    .add(createPrimaryMenuItem(menuItem, clickListener))
        }

        menuItemList
                .add(DividerDrawerItem())

        menuItemList
                .add(createPrimaryMenuItem(FileUploader, clickListener))

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
                .withIdentifier(menuItem.identifier)
                .withIcon(menuItem.getIcon(iconHandler))
                .withSelectable(menuItem.selectable)
                .withOnDrawerItemClickListener(clickListener)
    }

    private fun createSecondaryMenuItem(menuItem: DrawerMenuItem,
                                        clickListener: Drawer.OnDrawerItemClickListener): SecondaryDrawerItem {
        return SecondaryDrawerItem()
                .withName(menuItem.title)
                .withIdentifier(menuItem.identifier)
                .withIcon(menuItem.getIcon(iconHandler))
                .withSelectable(menuItem.selectable)
                .withOnDrawerItemClickListener(clickListener)
    }

    override fun onBackPressed() {
        if (navigationDrawer.isDrawerOpen) {
            navigationDrawer
                    .closeDrawer()
            return
        }

        super
                .onBackPressed()
    }
}