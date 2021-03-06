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

package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceDataProviderHolder
import de.markusressel.datamunch.preferences.KutePreferencesHolder
import de.markusressel.datamunch.view.IconHandler
import de.markusressel.datamunch.view.ThemeHelper
import javax.inject.Inject

abstract class DaggerBottomSheetFragmentBase : BottomSheetDialogFragment(),
        HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var themeHelper: ThemeHelper

    @Inject
    protected lateinit var iconHandler: IconHandler

    @Inject
    lateinit var preferencesDataProviderHolder: PreferenceDataProviderHolder

    @Inject
    lateinit var preferencesHolder: KutePreferencesHolder

    override fun onAttach(context: Context) {
        AndroidSupportInjection
                .inject(this)

        themeHelper
                .applyTheme(this, preferencesDataProviderHolder.dataProvider.getValueUnsafe(R.string.theme_key, getString(R.string.theme_dark_value)))

        super
                .onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    /**
     * The layout resource for this Activity
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val newContainer = inflater.inflate(layoutRes, container, false) as ViewGroup

        val alternative = super
                .onCreateView(inflater, newContainer, savedInstanceState)

        return alternative
                ?: newContainer
    }

}