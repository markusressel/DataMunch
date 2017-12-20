package de.markusressel.datamunch.application

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.support.annotation.StyleRes
import de.markusressel.datamunch.R
import de.markusressel.datamunch.preferences.PreferenceHandler
import de.markusressel.datamunch.preferences.PreferenceHandler.Companion.THEME
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Singleton
class ThemeHelper @Inject constructor() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    /**
     * Apply a Theme to an Activity
     *
     * @param activity Activity to apply theme on
     */
    fun applyTheme(activity: Activity) {
        val theme = preferenceHandler.getValue(THEME)
        when (theme) {
            context.getString(R.string.theme_light_value).toInt() -> setTheme(activity, R.style.AppTheme)
            context.getString(R.string.theme_dark_value).toInt() -> setTheme(activity, R.style.AppThemeDark)
            else -> setTheme(activity, R.style.AppThemeDark)
        }
    }

    /**
     * Apply a Theme to an Activity
     *
     * @param activity Activity to apply theme on
     */
    fun applyDialogTheme(activity: Activity) {
        val theme = preferenceHandler.getValue(THEME)
        when (theme) {
            context.getString(R.string.theme_light_value).toInt() -> setTheme(activity, R.style.AppDialogTheme)
            context.getString(R.string.theme_dark_value).toInt() -> setTheme(activity, R.style.AppDialogThemeDark)
            else -> setTheme(activity, R.style.AppDialogThemeDark)
        }
    }


    /**
     * Apply a Theme to a Fragment
     *
     * @param dialogFragment Fragment to apply theme on
     */
    fun applyDialogTheme(dialogFragment: DialogFragment) {
        val theme = preferenceHandler.getValue(THEME)
        when (theme) {
            context.getString(R.string.theme_light_value).toInt() -> dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme)
            context.getString(R.string.theme_dark_value).toInt() -> dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogThemeDark)
            else -> dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogThemeDark)
        }
    }

    private fun setTheme(activity: Activity, @StyleRes themeRes: Int) {
        activity.applicationContext
                .setTheme(themeRes)
        activity.setTheme(themeRes)
    }

}