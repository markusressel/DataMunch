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
                .withCloseOnClick(true)
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

        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(Navigator.DrawerItems.FileUploader.title)
                        .withIdentifier(Navigator.DrawerItems.FileUploader.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.DrawerItems.FileUploader.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateTo(this, Navigator.NavigationPages.FileUploaderPage)
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(Navigator.DrawerItems.Settings.title)
                        .withIdentifier(Navigator.DrawerItems.Settings.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.DrawerItems.Settings.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateTo(this, Navigator.NavigationPages.PreferencesOverviewPage)
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                SecondaryDrawerItem()
                        .withName(Navigator.DrawerItems.About.title)
                        .withIdentifier(Navigator.DrawerItems.About.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(Navigator.DrawerItems.About.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            navigator.navigateTo(this, Navigator.NavigationPages.AboutPage)
                            false
                        }
        )

        return menuItemList
    }
}