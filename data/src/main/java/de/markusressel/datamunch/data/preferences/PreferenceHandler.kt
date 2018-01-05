package de.markusressel.datamunch.data.preferences

import android.content.Context
import de.markusressel.datamunch.data.R
import de.markusressel.typedpreferences.PreferenceItem
import de.markusressel.typedpreferences.PreferencesHandlerBase
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Singleton
class PreferenceHandler @Inject
constructor(context: Context) : PreferencesHandlerBase(context) {

    // be sure to override the get() method
    override val sharedPreferencesName: String
        get() = "preferences"

    override val allPreferenceItems: Set<PreferenceItem<*>> = hashSetOf(
            THEME,
            FORCE_LOCALE,
            LOCALE
    )

    companion object {
        val THEME = PreferenceItem(R.string.theme_key, 0)
        val FORCE_LOCALE = PreferenceItem(R.string.force_locale_key, false)
        val LOCALE = PreferenceItem(R.string.locale_key, Locale.getDefault().toLanguageTag())
    }

}