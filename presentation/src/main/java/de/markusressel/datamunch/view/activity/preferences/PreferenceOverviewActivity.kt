package de.markusressel.datamunch.view.activity.preferences

import android.content.Intent
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
import de.markusressel.datamunch.view.ThemeHelper
import de.markusressel.datamunch.view.activity.MainActivity
import de.markusressel.typedpreferences.PreferenceItem
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */

class PreferenceOverviewActivity : de.mrapp.android.preference.activity.PreferenceActivity(), HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<android.support.v4.app.Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    internal lateinit var themeHelper: ThemeHelper

    override fun supportFragmentInjector(): AndroidInjector<android.support.v4.app.Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        initTheme()

        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTheme() {
        themeHelper.applyPreferencesTheme(this)
    }

    override fun onCreateNavigation(fragment: PreferenceFragment) {
        super.onCreateNavigation(fragment)
        fragment.addPreferencesFromResource(R.xml.preferences)

        setListeners()
    }

    private fun setListeners() {
        preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.THEME) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }
    }

    private fun restartActivity() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
