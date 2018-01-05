package de.markusressel.datamunch.dagger

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.CallSuper
import android.support.v7.preference.PreferenceFragmentCompat
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.AndroidSupportInjection
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */

abstract class DaggerPreferenceFragment : PreferenceFragmentCompat(), HasFragmentInjector, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

    override fun onResume() {
        super.onResume()

        preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)

        super.onPause()
    }

    @CallSuper
    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        preferenceHandler.forceRefreshCache()
    }

}
