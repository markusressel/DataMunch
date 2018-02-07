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
import de.mrapp.android.preference.activity.PreferenceFragment
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */
abstract class DaggerPreferenceFragment : PreferenceFragment(), HasFragmentInjector,
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
    abstract protected fun findPreferences()

    /**
     * Use this method to update the summary of preferences that need to change
     * depending on it's current value
     */
    abstract protected fun updateSummaries()

    /**
     * Return the xml resource for this preference screen
     */
    @XmlRes
    abstract protected fun getPreferencesResource(): Int

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
