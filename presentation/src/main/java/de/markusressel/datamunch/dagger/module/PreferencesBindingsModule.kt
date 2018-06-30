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

package de.markusressel.datamunch.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.data.preferences.PreferenceDataProviderHolder
import de.markusressel.datamunch.view.activity.preferences.PreferenceActivityBase
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity
import de.markusressel.datamunch.view.fragment.base.DaggerKutePreferenceFragmentBase
import de.markusressel.datamunch.view.fragment.preferences.DaggerPreferenceFragment
import de.markusressel.datamunch.view.fragment.preferences.LifecyclePreferenceFragmentBase
import de.markusressel.datamunch.view.fragment.preferences.MainPreferenceFragment
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Module
abstract class PreferencesBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun PreferenceActivityBase(): PreferenceActivityBase

    @ContributesAndroidInjector
    internal abstract fun preferenceOverviewActivity(): PreferenceOverviewActivity

    @ContributesAndroidInjector
    internal abstract fun DaggerPreferenceFragment(): DaggerPreferenceFragment

    @ContributesAndroidInjector
    internal abstract fun DaggerKutePreferenceFragmentBase(): DaggerKutePreferenceFragmentBase

    @ContributesAndroidInjector
    internal abstract fun LifecyclePreferenceFragmentBase(): LifecyclePreferenceFragmentBase

    @ContributesAndroidInjector
    internal abstract fun MainPreferenceFragment(): MainPreferenceFragment

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        internal fun providePreferenceDataProviderHolder(
                context: Context): PreferenceDataProviderHolder {
            return PreferenceDataProviderHolder(context)
        }

    }

}