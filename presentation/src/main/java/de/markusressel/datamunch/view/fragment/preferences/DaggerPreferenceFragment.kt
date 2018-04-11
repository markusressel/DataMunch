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

package de.markusressel.datamunch.view.fragment.preferences

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.annotation.XmlRes
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */
abstract class DaggerPreferenceFragment : LifecyclePreferenceFragmentBase(), HasFragmentInjector,
    SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    override fun onAttach(context: Context?) {
        AndroidInjection
                .inject(this)

        super
                .onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        addPreferencesFromResource(getPreferencesResource())

        findPreferences()
        updateSummaries()
    }

    override fun onStart() {
        super
                .onStart()

        preferenceManager
                .sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super
                .onResume()

        updateSummaries()
    }

    /**
     * Use this method to get a reference to Preference instances of this page
     */
    protected abstract fun findPreferences()

    /**
     * Use this method to update the summary of preferences that need to change
     * depending on it's current value
     */
    protected abstract fun updateSummaries()

    /**
     * Return the xml resource for this preference screen
     */
    @XmlRes
    protected abstract fun getPreferencesResource(): Int

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        updateSummaries()
    }

    override fun onStop() {
        super
                .onStop()

        preferenceManager
                .sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)


    }

}
