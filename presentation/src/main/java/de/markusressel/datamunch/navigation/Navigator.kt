package de.markusressel.datamunch.navigation

import android.content.Context
import android.content.Intent
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.util.Colors
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.activity.fileuploader.FileUploaderActivity
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity2
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 07.01.2018.
 */
@Singleton
class Navigator @Inject constructor() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    fun navigateToFileUploader() {
        val intent = Intent(context, FileUploaderActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToPreferences() {
        val intent = Intent(context, PreferenceOverviewActivity2::class.java)
        context.startActivity(intent)
    }

    fun navigateToAbout() {
        val themeVal = preferenceHandler.getValue(PreferenceHandler.THEME)

        val aboutLibTheme: Libs.ActivityStyle
        if (themeVal == context.getString(R.string.theme_light_value).toInt()) {
            aboutLibTheme = Libs.ActivityStyle.LIGHT_DARK_TOOLBAR
        } else {
            aboutLibTheme = Libs.ActivityStyle.DARK
        }

        LibsBuilder()
                .withActivityStyle(aboutLibTheme)
                .withActivityColor(Colors(
                        ContextCompat.getColor(context, R.color.primaryColor),
                        ContextCompat.getColor(context, R.color.primaryColor_dark)))
                .withActivityTitle(context.getString(R.string.menu_item_about))
                .start(context)
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