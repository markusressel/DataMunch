package de.markusressel.datamunch.view.activity.preferences

import android.content.Intent
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v4.util.SparseArrayCompat
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.navigation.Navigator
import de.markusressel.typedpreferences.PreferenceItem
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */
class PreferenceOverviewActivity : PreferenceActivityBase() {

    @Inject
    internal lateinit var navigator: Navigator

    private lateinit var themeMap: SparseArrayCompat<String>
    private lateinit var themePreference: Preference

    private lateinit var localeMap: SparseArrayCompat<String>
    private lateinit var localePreference: Preference

    private var themeListener: ((PreferenceItem<Int>, Int, Int) -> Unit)? = null
    private var localeListener: ((PreferenceItem<Int>, Int, Int) -> Unit)? = null

    override fun onStart() {
        super
                .onStart()

        setListeners()
    }

    override fun getPreferencesResource(): Int {
        return R
                .xml
                .preferences
    }

    override fun findPreferences(fragment: PreferenceFragment) {
        themeMap = getListPreferenceEntryValueMap(R.array.theme_values, R.array.theme_names)
        themePreference = fragment
                .findPreference(getString(R.string.theme_key))

        localeMap = getListPreferenceEntryValueMap(R.array.locale_values, R.array.locale_names)
        localePreference = fragment
                .findPreference(getString(R.string.locale_key))
    }

    override fun updateSummaries() {
        themePreference
                .summary = themeMap[preferenceHandler.getValue(PreferenceHandler.THEME)]
        localePreference
                .summary = localeMap[preferenceHandler.getValue(PreferenceHandler.LOCALE)]
    }

    private fun setListeners() {
        themeListener = preferenceHandler
                .addOnPreferenceChangedListener(PreferenceHandler.THEME) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
                    restartActivity()
                }

        localeListener = preferenceHandler
                .addOnPreferenceChangedListener(PreferenceHandler.LOCALE) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
                    restartActivity()
                    recreate()
                }
    }

    private fun restartActivity() {
        finish()

        navigator
                .startActivity(this, Navigator.NavigationPages.MainPage,
                               Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        navigator
                .navigateTo(this, Navigator.NavigationPages.PreferencesOverviewPage)
    }

    override fun onStop() {
        super
                .onStop()

        themeListener
                ?.let {
                    preferenceHandler
                            .removeOnPreferenceChangedListener(it)
                }

        localeListener
                ?.let {
                    preferenceHandler
                            .removeOnPreferenceChangedListener(it)
                }
    }

}
