package de.markusressel.datamunch.data.preferences

import android.content.Context
import de.markusressel.datamunch.data.R
import de.markusressel.typedpreferences.PreferenceItem
import de.markusressel.typedpreferences.PreferencesHandlerBase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Singleton
class PreferenceHandler @Inject
constructor(context: Context) : PreferencesHandlerBase(context) {

    // be sure to override the get() method
    override var sharedPreferencesName: String? = null

    override val allPreferenceItems: Set<PreferenceItem<*>> = hashSetOf(
            THEME,
            LOCALE,
            CONNECTION_HOST
    )

    companion object {
        val THEME = PreferenceItem(R.string.theme_key, 0)
        val LOCALE = PreferenceItem(R.string.locale_key, 0)
        val CONNECTION_HOST = PreferenceItem(R.string.connection_host_key, "frittenbude.markusressel.de")
    }

}