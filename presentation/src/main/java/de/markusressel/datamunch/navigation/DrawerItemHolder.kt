package de.markusressel.datamunch.navigation

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(
            identifier = 10,
            title = R.string.menu_item_status,
            icon = MaterialDesignIconic.Icon.gmi_home,
            selectable = true)

    val Jails = DrawerMenuItem(
            identifier = 20,
            title = R.string.menu_item_jails,
            drawableRes = R.drawable.cube_outline,
            selectable = true)

    val FileUploader = DrawerMenuItem(
            identifier = 30,
            title = R.string.menu_item_file_uploader,
            icon = MaterialDesignIconic.Icon.gmi_file,
            selectable = true)

    val Settings = DrawerMenuItem(
            identifier = 40,
            title = R.string.menu_item_settings,
            icon = MaterialDesignIconic.Icon.gmi_settings,
            selectable = true)

    val About = DrawerMenuItem(
            identifier = 50,
            title = R.string.menu_item_about,
            icon = MaterialDesignIconic.Icon.gmi_info,
            selectable = false)

}