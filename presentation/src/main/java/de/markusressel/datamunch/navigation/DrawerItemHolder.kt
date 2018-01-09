package de.markusressel.datamunch.navigation

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val Status = DrawerMenuItem(10,
            R.string.menu_item_status,
            MaterialDesignIconic.Icon.gmi_home,
            true)

    val FileUploader = DrawerMenuItem(20,
            R.string.menu_item_file_uploader,
            MaterialDesignIconic.Icon.gmi_file,
            true)

    val Settings = DrawerMenuItem(30,
            R.string.menu_item_settings,
            MaterialDesignIconic.Icon.gmi_settings,
            true)

    val About = DrawerMenuItem(
            40,
            R.string.menu_item_about,
            MaterialDesignIconic.Icon.gmi_info,
            false)

}