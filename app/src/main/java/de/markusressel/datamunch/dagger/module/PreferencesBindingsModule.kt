package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.gui.preferences.PreferenceOverviewActivity
import de.markusressel.datamunch.gui.preferences.PreferencesFragment

/**
 * Created by Markus on 20.12.2017.
 */
@Module
abstract class PreferencesBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun preferenceOverviewActivity(): PreferenceOverviewActivity

    @ContributesAndroidInjector
    internal abstract fun preferenceOverviewFragment(): PreferencesFragment

}