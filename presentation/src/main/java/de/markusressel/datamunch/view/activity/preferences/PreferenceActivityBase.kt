package de.markusressel.datamunch.view.activity.preferences

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.ThemeHelper
import javax.inject.Inject

/**
 * Base class for all PreferenceActivities
 *
 * Created by Markus on 08.01.2018.
 */
abstract class PreferenceActivityBase : de.mrapp.android.preference.activity.PreferenceActivity(), HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    internal lateinit var themeHelper: ThemeHelper

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        themeHelper.applyPreferencesTheme(this)

        super.onCreate(savedInstanceState)

        initTheme()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTheme() {
        setNavigationBackgroundColor(themeHelper.getThemeAttrColor(this, android.R.attr.windowBackground))
    }

}