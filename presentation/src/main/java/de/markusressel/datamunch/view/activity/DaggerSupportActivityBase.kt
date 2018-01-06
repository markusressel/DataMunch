/*
 *  PowerSwitch by Max Rosin & Markus Ressel
 *  Copyright (C) 2015  Markus Ressel
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

package de.markusressel.datamunch.view.activity

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.IntDef
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Window
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.presenatation.ThemeHelper
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Markus on 20.12.2017.
 */
abstract class DaggerSupportActivityBase : AppCompatActivity(), HasFragmentInjector, HasSupportFragmentInjector {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    @Inject
    internal lateinit var themeHelper: ThemeHelper

    /**
     * @return true if this activity should use a dialog theme instead of a normal activity theme
     */
    @get:Style
    protected abstract val style: Int

    /**
     * The layout ressource for this Activity
     */
    protected abstract val layoutRes: Int

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        // apply forced locale (if set in developer options)
        initLocale()

        // set Theme before anything else in onCreate();
        initTheme()

        super.onCreate(savedInstanceState)

        if (style == DIALOG) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        setContentView(layoutRes)

        setSupportActionBar(toolbar)
    }

    fun initLocale() {
        val forceLanguage = preferenceHandler.getValue(PreferenceHandler.FORCE_LOCALE)
        if (forceLanguage) {
            val localeString = preferenceHandler.getValue(PreferenceHandler.LOCALE)
            val locale = Locale(localeString)

            val res = resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
        }
    }

    fun initTheme() {
        if (style == DIALOG) {
            themeHelper.applyDialogTheme(this)//set up notitle
        } else {
            themeHelper.applyTheme(this)
        }
    }

    @IntDef(DEFAULT.toLong(), DIALOG.toLong())
    @kotlin.annotation.Retention
    annotation class Style

    companion object {

        /**
         * Normal activity style
         */
        const val DEFAULT = 0

        /**
         * Normal activity style
         */
        const val DIALOG = 1
    }

}
