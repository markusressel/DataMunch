package de.markusressel.datamunch.view.fragment.preferences

import android.preference.Preference
import de.markusressel.datamunch.R
import de.mrapp.android.preference.activity.PreferenceFragment
import de.mrapp.android.preference.activity.RestoreDefaultsListener

/**
 * Created by Markus on 06.01.2018.
 */
class SecurityPreferences : DaggerPreferenceFragment(), RestoreDefaultsListener {

    override fun getPreferencesResource(): Int {
        return R
                .xml
                .preferences_security
    }

    override fun findPreferences() {
    }

    override fun updateSummaries() {
    }

    override fun onRestoredDefaultValue(fragment: PreferenceFragment, preference: Preference,
                                        oldValue: Any?, newValue: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValueRequested(fragment: PreferenceFragment,
                                                preference: Preference,
                                                currentValue: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValuesRequested(fragment: PreferenceFragment): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}