package de.markusressel.datamunch.view.activity

import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.Navigator
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

/**
 * A base activity that provides the full navigation drawer implementation
 *
 * Created by Markus on 07.01.2018.
 */
abstract class NavigationDrawerActivity : DaggerSupportActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuItemList = initDrawerMenuItems()
        val accountHeader = initAccountHeader()

        DrawerBuilder().withActivity(this)
                .withAccountHeader(accountHeader)
                .withDrawerItems(menuItemList)
                .withToolbar(toolbar)
                .build()
    }

    private fun initAccountHeader(): AccountHeader {
        val profiles: MutableList<IProfile<*>> = getProfiles()

        return AccountHeaderBuilder()
                .withActivity(this)
                .withProfiles(profiles)
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
                        .withIcon(R.drawable.leak_canary_icon)
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

        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(Navigator.Companion.DrawerMenuItem.FileUploader.name)
                        .withIdentifier(Navigator.Companion.DrawerMenuItem.FileUploader.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.Companion.DrawerMenuItem.FileUploader.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateToFileUploader()
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(Navigator.Companion.DrawerMenuItem.Settings.name)
                        .withIdentifier(Navigator.Companion.DrawerMenuItem.Settings.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.Companion.DrawerMenuItem.Settings.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateToPreferences()
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                SecondaryDrawerItem()
                        .withName(Navigator.Companion.DrawerMenuItem.About.title)
                        .withIdentifier(Navigator.Companion.DrawerMenuItem.About.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.Companion.DrawerMenuItem.About.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateToAbout()
                            false
                        }
        )

        return menuItemList
    }
}