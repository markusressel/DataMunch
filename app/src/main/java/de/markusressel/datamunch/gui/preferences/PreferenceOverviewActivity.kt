package de.markusressel.datamunch.gui.preferences

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.dagger.DaggerSupportActivityBase

/**
 * Created by Markus on 20.12.2017.
 */
class PreferenceOverviewActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val preferencesFragment = PreferencesFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, preferencesFragment)
                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}