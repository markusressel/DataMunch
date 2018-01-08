package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.preferences.PreferenceActivityBase
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity

/**
 * Created by Markus on 20.12.2017.
 */
@Module
abstract class PreferencesBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun PreferenceActivityBase(): PreferenceActivityBase

    @ContributesAndroidInjector
    internal abstract fun preferenceOverviewActivity(): PreferenceOverviewActivity

}