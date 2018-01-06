package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.PreferencesFragment

/**
 * Created by Markus on 20.12.2017.
 */
class PreferenceOverviewActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DIALOG

    override val layoutRes: Int
        get() = R.layout.activity_preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle(R.string.menu_item_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferencesFragment = PreferencesFragment()

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, preferencesFragment)
//                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}