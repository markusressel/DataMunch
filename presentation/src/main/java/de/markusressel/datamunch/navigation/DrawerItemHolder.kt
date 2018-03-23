package de.markusressel.datamunch.navigation

import com.github.ajalt.timberkt.Timber
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(identifier = 10, title = R.string.menu_item_status,
                                icon = MaterialDesignIconic.Icon.gmi_home, selectable = true,
                                navigationPage = NavigationPageHolder.Main)

    val Accounts = DrawerMenuItem(identifier = 15, title = R.string.menu_item_accounts,
                                  icon = MaterialDesignIconic.Icon.gmi_account, selectable = true,
                                  navigationPage = NavigationPageHolder.Accounts)

    val Services = DrawerMenuItem(identifier = 20, title = R.string.menu_item_services,
                                  drawableRes = R.drawable.cube_outline, selectable = true,
                                  navigationPage = NavigationPageHolder.Services)

    val Sharing = DrawerMenuItem(identifier = 32, title = R.string.menu_item_sharing,
                                 icon = MaterialDesignIconic.Icon.gmi_folder_shared,
                                 selectable = true, navigationPage = NavigationPageHolder.Sharing)

    val Storage = DrawerMenuItem(identifier = 25, title = R.string.menu_item_storage,
                                 icon = MaterialDesignIconic.Icon.gmi_storage, selectable = true,
                                 navigationPage = NavigationPageHolder.Storage)

    val Jails = DrawerMenuItem(identifier = 30, title = R.string.menu_item_jails,
                               drawableRes = R.drawable.ic_jail, selectable = true,
                               navigationPage = NavigationPageHolder.Jails)

    val Plugins = DrawerMenuItem(identifier = 40, title = R.string.menu_item_plugins,
                                 icon = MaterialDesignIconic.Icon.gmi_puzzle_piece,
                                 selectable = true, navigationPage = NavigationPageHolder.Plugins)

    val FileUploader = DrawerMenuItem(identifier = 50, title = R.string.menu_item_file_uploader,
                                      icon = MaterialDesignIconic.Icon.gmi_file, selectable = true,
                                      navigationPage = NavigationPageHolder.FileUploader)

    val System = DrawerMenuItem(identifier = 60, title = R.string.menu_item_system,
                                icon = MaterialDesignIconic.Icon.gmi_settings, selectable = true,
                                navigationPage = NavigationPageHolder.System)

    val Tasks = DrawerMenuItem(identifier = 70, title = R.string.menu_item_tasks,
                               icon = MaterialDesignIconic.Icon.gmi_assignment_check,
                               selectable = true, navigationPage = NavigationPageHolder.Tasks)

    val Settings = DrawerMenuItem(identifier = 500, title = R.string.menu_item_settings,
                                  icon = MaterialDesignIconic.Icon.gmi_settings, selectable = false,
                                  navigationPage = NavigationPageHolder.Settings)

    val About = DrawerMenuItem(identifier = 1000, title = R.string.menu_item_about,
                               icon = MaterialDesignIconic.Icon.gmi_info, selectable = false,
                               navigationPage = NavigationPageHolder.About)

    fun fromId(drawerItemIdentifier: Long): DrawerMenuItem? {
        return when (drawerItemIdentifier) {
            Status.identifier -> Status
            Accounts.identifier -> Accounts
            Services.identifier -> Services
            Sharing.identifier -> Sharing
            Storage.identifier -> Storage
            Jails.identifier -> Jails
            Plugins.identifier -> Plugins
            System.identifier -> System
            Tasks.identifier -> Tasks
            FileUploader.identifier -> FileUploader
            Settings.identifier -> Settings
            About.identifier -> About
            else -> {
                Timber
                        .w { "Unknown menu item identifier: $drawerItemIdentifier" }
                null
            }
        }
    }

}