package de.markusressel.datamunch.view.fragment.preferences

import android.preference.Preference
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.fragment.DaggerPreferenceFragment
import de.mrapp.android.preference.activity.PreferenceFragment
import de.mrapp.android.preference.activity.RestoreDefaultsListener

/**
 * Created by Markus on 06.01.2018.
 */
class ConnectionPreferences : DaggerPreferenceFragment(), RestoreDefaultsListener {

    private lateinit var hostPreference: Preference

    override fun getPreferencesResource(): Int {
        return R.xml.preferences_connection
    }

    override fun findPreferences() {
        hostPreference = findPreference(getString(R.string.connection_host_key))
    }

    override fun updateSummaries() {
        hostPreference.summary = preferenceHandler.getValue(PreferenceHandler.CONNECTION_HOST, false)
    }

    override fun onRestoredDefaultValue(fragment: PreferenceFragment, preference: Preference, oldValue: Any?, newValue: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValueRequested(fragment: PreferenceFragment, preference: Preference, currentValue: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValuesRequested(fragment: PreferenceFragment): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}