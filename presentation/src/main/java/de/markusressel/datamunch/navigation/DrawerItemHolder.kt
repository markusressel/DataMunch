package de.markusressel.datamunch.navigation

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(identifier = 10, title = R.string.menu_item_status,
                                icon = MaterialDesignIconic.Icon.gmi_home, selectable = true)

    val Accounts = DrawerMenuItem(identifier = 15, title = R.string.menu_item_accounts,
                                  icon = MaterialDesignIconic.Icon.gmi_account, selectable = true)

    val Services = DrawerMenuItem(identifier = 20, title = R.string.menu_item_services,
                                  drawableRes = R.drawable.cube_outline, selectable = true)

    val Sharing = DrawerMenuItem(identifier = 32, title = R.string.menu_item_sharing,
                                 icon = MaterialDesignIconic.Icon.gmi_folder_shared,
                                 selectable = true)

    val Storage = DrawerMenuItem(identifier = 25, title = R.string.menu_item_storage,
                                 icon = MaterialDesignIconic.Icon.gmi_storage, selectable = true)

    val Jails = DrawerMenuItem(identifier = 30, title = R.string.menu_item_jails,
                               drawableRes = R.drawable.ic_jail, selectable = true)

    val Plugins = DrawerMenuItem(identifier = 40, title = R.string.menu_item_plugins,
                                 icon = MaterialDesignIconic.Icon.gmi_puzzle_piece,
                                 selectable = true)

    val FileUploader = DrawerMenuItem(identifier = 50, title = R.string.menu_item_file_uploader,
                                      icon = MaterialDesignIconic.Icon.gmi_file, selectable = true)

    val System = DrawerMenuItem(identifier = 50, title = R.string.menu_item_system,
                                icon = MaterialDesignIconic.Icon.gmi_settings, selectable = true)

    val Settings = DrawerMenuItem(identifier = 500, title = R.string.menu_item_settings,
                                  icon = MaterialDesignIconic.Icon.gmi_settings, selectable = true)

    val About = DrawerMenuItem(identifier = 1000, title = R.string.menu_item_about,
                               icon = MaterialDesignIconic.Icon.gmi_info, selectable = false)

}