package de.markusressel.datamunch.gui.preferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.dagger.DaggerPreferenceFragment
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.presenatation.MainActivity
import de.markusressel.typedpreferences.PreferenceItem

/**
 * Created by Markus on 20.12.2017.
 */
class PreferencesFragment : DaggerPreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // set preferences file name
        preferenceManager.sharedPreferencesName = preferenceHandler.sharedPreferencesName

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences)

        initializePreferenceItems()
        setListeners()
    }

    private fun initializePreferenceItems() {

    }

    private fun setListeners() {
        preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.THEME) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }
    }

    private fun restartActivity() {
        activity?.finish()
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        activity?.let {
            when (key) {
                PreferenceHandler.THEME.getKey(it) -> restartActivity()
            }
        }

        super.onSharedPreferenceChanged(preferences, key)
    }
}