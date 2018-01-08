package de.markusressel.datamunch.view.activity.preferences

import android.content.Intent
import android.preference.PreferenceFragment
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.activity.MainActivity
import de.markusressel.typedpreferences.PreferenceItem

/**
 * Created by Markus on 15.07.2017.
 */

class PreferenceOverviewActivity : PreferenceActivityBase() {

    override fun onCreateNavigation(fragment: PreferenceFragment) {
        super.onCreateNavigation(fragment)
        fragment.addPreferencesFromResource(R.xml.preferences)

        setListeners()
    }

    private fun setListeners() {
        preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.THEME) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }

        preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.LOCALE) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }
    }

    private fun restartActivity() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        val intentPreferences = Intent(this, PreferenceOverviewActivity::class.java)
        startActivity(intentPreferences)
    }

}
