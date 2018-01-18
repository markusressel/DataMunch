package de.markusressel.datamunch.view.activity.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.annotation.ArrayRes
import android.support.annotation.CallSuper
import android.support.annotation.XmlRes
import android.support.v4.app.Fragment
import android.support.v4.util.SparseArrayCompat
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.ThemeHelper
import javax.inject.Inject

/**
 * Base class for all PreferenceActivities
 *
 * Created by Markus on 08.01.2018.
 */
abstract class PreferenceActivityBase : de.mrapp.android.preference.activity.PreferenceActivity(), HasFragmentInjector, HasSupportFragmentInjector, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }


    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    @Inject
    internal lateinit var themeHelper: ThemeHelper

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        themeHelper.applyPreferencesTheme(this)

        super.onCreate(savedInstanceState)

        initTheme()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(R.string.menu_item_settings)
    }

    override fun onCreateNavigation(fragment: PreferenceFragment) {
        super.onCreateNavigation(fragment)
        fragment.addPreferencesFromResource(getPreferencesResource())
    }

    override fun onStart() {
        super.onStart()

        navigationFragment?.let {
            findPreferences(it)
            it.preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }
    }

    override fun onResume() {
        super.onResume()

        navigationFragment?.let {
            updateSummaries()
        }
    }

    /**
     * Use this method to get a reference to Preference instances of this page
     */
    abstract protected fun findPreferences(fragment: PreferenceFragment)

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

    private fun initTheme() {
        setNavigationBackgroundColor(themeHelper.getThemeAttrColor(this, android.R.attr.windowBackground))
    }

    /**
     * Gets a Map from two array resources
     *
     * @param valueRes values stored in preferences
     * @param nameRes  name/description of this option used in view
     *
     * @return Map from stored value -> display name
     */
    protected fun getListPreferenceEntryValueMap(@ArrayRes valueRes: Int, @ArrayRes nameRes: Int): SparseArrayCompat<String> {
        val map = SparseArrayCompat<String>()

        val values = resources.getStringArray(valueRes)
        val names = resources.getStringArray(nameRes)

        for (i in values.indices) {
            map.put(Integer.valueOf(values[i])!!, names[i])
        }

        return map
    }

    override fun onStop() {
        super.onStop()

        navigationFragment?.let {
            it.preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }
    }

}