package de.markusressel.datamunch.navigation

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

/**
 * Created by Markus on 08.01.2018.
 */
object DrawerItemHolder {

    val FileUploader = DrawerMenuItem(10,
            R.string.menu_item_file_uploader,
            MaterialDesignIconic.Icon.gmi_file)

    val Settings = DrawerMenuItem(20,
            R.string.menu_item_settings,
            MaterialDesignIconic.Icon.gmi_settings)

    val About = DrawerMenuItem(
            30,
            R.string.menu_item_about,
            MaterialDesignIconic.Icon.gmi_info)

}