/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
                .addOnPreferenceChangedListener(
                        PreferenceHandler.THEME) { _: PreferenceItem<Int>, _: Int, _: Int ->
                    restartActivity()
                }

        localeListener = preferenceHandler
                .addOnPreferenceChangedListener(
                        PreferenceHandler.LOCALE) { _: PreferenceItem<Int>, _: Int, _: Int ->
                    restartActivity()
                    recreate()
                }
    }

    private fun restartActivity() {
        navigator
                .startActivity(this, Navigator.NavigationPages.Main,
                               Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        navigator
                .startActivity(this, Navigator.NavigationPages.Settings)
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
