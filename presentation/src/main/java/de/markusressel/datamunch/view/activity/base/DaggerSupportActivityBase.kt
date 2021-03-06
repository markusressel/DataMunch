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

package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceDataProviderHolder
import de.markusressel.datamunch.preferences.KutePreferencesHolder
import de.markusressel.datamunch.view.IconHandler
import de.markusressel.datamunch.view.ThemeHelper
import de.markusressel.datamunch.view.component.LockComponent
import kotlinx.android.synthetic.main.view_toolbar.*
import java.util.*
import javax.inject.Inject

/**
 * Created by Markus on 20.12.2017.
 */
abstract class DaggerSupportActivityBase : LifecycleActivityBase(), HasFragmentInjector,
        HasSupportFragmentInjector {

    @Inject
    internal lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    @Inject
    lateinit var preferencesDataProviderHolder: PreferenceDataProviderHolder

    @Inject
    protected lateinit var preferencesHolder: KutePreferencesHolder

    @Inject
    protected lateinit var themeHelper: ThemeHelper

    @Inject
    protected lateinit var iconHandler: IconHandler

    /**
     * @return true if this activity should use a dialog theme instead of a normal activity theme
     */
    @get:Style
    protected abstract val style: Int

    /**
     * The layout ressource for this Activity
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment>? {
        return frameworkFragmentInjector
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection
                .inject(this)

        // apply forced locale (if set in developer options)
        initLocale()

        // set Theme before anything else in onCreate();
        initTheme()

        if (style == FULLSCREEN) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            hideStatusBar()
        } else if (style == DIALOG) {
            // Hide title on dialogs to use view_toolbar instead
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        super
                .onCreate(savedInstanceState)

        // inflate view manually so it can be altered in plugins
        val contentView = layoutInflater
                .inflate(layoutRes, null)
        setContentView(contentView)

        setSupportActionBar(toolbar)
        supportActionBar
                ?.setDisplayHomeAsUpEnabled(true)

        onContentViewInflated(savedInstanceState)

        Bus
                .observe<LockComponent.SetStatusBarStateEvent>()
                .subscribe {
                    if (it.visible) {
                        showStatusBar()
                    } else {
                        hideStatusBar()
                    }
                }
                .registerInBus(this)
    }

    open fun onContentViewInflated(savedInstanceState: Bundle?) {
    }

    /**
     * Show the status bar
     */
    protected fun showStatusBar() {
        window
                .clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * Hide the status bar
     */
    protected fun hideStatusBar() {
        window
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun initLocale() {
        val localeValue = preferencesHolder
                .language
                .persistedValue

        when (localeValue) {
            getString(R.string.locale_EN_value) -> setLocale(Locale.ENGLISH)
            getString(R.string.locale_DE_value) -> setLocale(Locale.GERMAN)
        }
    }

    private fun setLocale(locale: Locale) {
        val res = resources
        // Change locale settings in the app.
        val dm = res
                .displayMetrics
        val conf = res
                .configuration

        conf
                .locale = locale
        conf
                .setLocale(locale)
        res
                .updateConfiguration(conf, dm)

        //        onConfigurationChanged(conf)
    }

    private fun initTheme() {
        val theme = preferencesDataProviderHolder.dataProvider.getValueUnsafe(R.string.theme_key, getString(R.string.theme_dark_value))

        if (style == DIALOG) {
            themeHelper
                    .applyDialogTheme(this, theme) //set up notitle
        } else {
            themeHelper
                    .applyTheme(this, theme)
        }
    }

    @IntDef(DEFAULT, DIALOG)
    @kotlin.annotation.Retention
    annotation class Style

    companion object {

        /**
         * Normal activity style
         */
        const val DEFAULT = 0

        /**
         * Dialog style
         */
        const val DIALOG = 1

        /**
         * Fullscreen activity style
         */
        const val FULLSCREEN = 2
    }

}
