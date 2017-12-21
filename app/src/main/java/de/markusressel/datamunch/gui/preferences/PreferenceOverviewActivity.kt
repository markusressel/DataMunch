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

    override val layoutRes: Int
        get() = R.layout.activity_preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle(R.string.menu_item_settings)

        val preferencesFragment = PreferencesFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, preferencesFragment)
//                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}