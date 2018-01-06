package de.markusressel.datamunch.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.github.ajalt.timberkt.Timber
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.util.Colors
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
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
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.presenatation.IconicsHelper
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity2
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_main

    @Inject
    lateinit var iconicsHelper: IconicsHelper

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
                        .withName(DrawerMenuItem.FileUploader.name)
                        .withIdentifier(DrawerMenuItem.FileUploader.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(DrawerMenuItem.FileUploader.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            val intent = Intent(this, FileUploaderActivity::class.java)
                            startActivity(intent)
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                PrimaryDrawerItem()
                        .withName(DrawerMenuItem.Settings.name)
                        .withIdentifier(DrawerMenuItem.Settings.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(DrawerMenuItem.Settings.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                            val intent = Intent(this, PreferenceOverviewActivity2::class.java)
                            startActivity(intent)
                            false
                        }
        )

        menuItemList.add(
                DividerDrawerItem()
        )

        menuItemList.add(
                SecondaryDrawerItem()
                        .withName(DrawerMenuItem.About.title)
                        .withIdentifier(DrawerMenuItem.About.identifier)
                        .withIcon(iconicsHelper.getMenuIcon(DrawerMenuItem.About.icon))
                        .withOnDrawerItemClickListener { view, i, iDrawerItem ->

                            val themeVal = preferenceHandler.getValue(PreferenceHandler.THEME)

                            val aboutLibTheme: Libs.ActivityStyle
                            if (themeVal == getString(R.string.theme_light_value).toInt()) {
                                aboutLibTheme = Libs.ActivityStyle.LIGHT_DARK_TOOLBAR
                            } else {
                                aboutLibTheme = Libs.ActivityStyle.DARK
                            }

                            LibsBuilder()
                                    .withActivityStyle(aboutLibTheme)
                                    .withActivityColor(Colors(
                                            ContextCompat.getColor(this, R.color.primaryColor),
                                            ContextCompat.getColor(this, R.color.primaryColor_dark)))
                                    .withActivityTitle(getString(R.string.menu_item_about))
                                    .start(this)

                            false
                        }
        )

        return menuItemList
    }

    companion object {

        enum class DrawerMenuItem(
                val identifier: Long,
                @StringRes
                val title: Int,
                val icon: IIcon) {
            FileUploader(
                    10,
                    R.string.menu_item_file_uploader,
                    MaterialDesignIconic.Icon.gmi_file),
            Settings(20,
                    R.string.menu_item_settings,
                    MaterialDesignIconic.Icon.gmi_settings),
            About(30,
                    R.string.menu_item_about,
                    MaterialDesignIconic.Icon.gmi_info)
        }
    }
}