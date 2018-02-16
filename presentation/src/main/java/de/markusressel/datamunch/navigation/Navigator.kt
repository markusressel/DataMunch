package de.markusressel.datamunch.navigation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.util.Colors
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.navigation.page.NavigationPage
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 07.01.2018.
 */
@Singleton
class Navigator @Inject constructor() {

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    /**
     * Navigate to a specific page
     *
     * @param activityContext activity context
     * @param page the page to navigate to
     */
    fun startActivity(activityContext: Context, page: NavigationPage) {
        startActivity(activityContext, page, null)
    }

    /**
     * Navigate to a specific page
     */
    fun navigateTo(activityContext: AppCompatActivity, page: NavigationPage) {
        val fragment = page.fragment!!()

        activityContext
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, fragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

    /**
     * Navigate to a specific page using the passed in flags
     *
     * @param activityContext activity context
     * @param page the page to navigate to
     * @param flags Intent flags
     */
    fun startActivity(activityContext: Context, page: NavigationPage, flags: Int?) {
        if (page == NavigationPages.AboutPage) {
            navigateToAbout(activityContext)
            return
        }

        val intent = page
                .createIntent(activityContext)
        flags
                ?.let {
                    intent
                            .addFlags(it)
                }

        activityContext
                .startActivity(intent)
    }

    private fun navigateToAbout(activityContext: Context) {
        val themeVal = preferenceHandler
                .getValue(PreferenceHandler.THEME)

        val aboutLibTheme: Libs.ActivityStyle
        if (themeVal == activityContext.getString(R.string.theme_light_value).toInt()) {
            aboutLibTheme = Libs
                    .ActivityStyle
                    .LIGHT_DARK_TOOLBAR
        } else {
            aboutLibTheme = Libs
                    .ActivityStyle
                    .DARK
        }

        LibsBuilder()
                .withActivityStyle(aboutLibTheme)
                .withActivityColor(
                        Colors(ContextCompat.getColor(activityContext, R.color.primaryColor),
                               ContextCompat.getColor(activityContext, R.color.primaryColor_dark)))
                .withActivityTitle(activityContext.getString(R.string.menu_item_about))
                .start(activityContext)
    }

    companion object {
        val DrawerItems = DrawerItemHolder
        val NavigationPages = NavigationPageHolder
    }

}