package de.markusressel.datamunch.view.activity.preferences

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.annotation.CallSuper
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.presenatation.ThemeHelper
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */

class PreferenceOverviewActivity2 : de.mrapp.android.preference.activity.PreferenceActivity(), HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    internal lateinit var themeHelper: ThemeHelper

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        initTheme()

        themeHelper.applyPreferencesTheme(this)

        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTheme() {


    }

    override fun onCreateNavigation(fragment: PreferenceFragment) {
        super.onCreateNavigation(fragment)
        fragment.addPreferencesFromResource(R.xml.preferences)
    }

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

}
