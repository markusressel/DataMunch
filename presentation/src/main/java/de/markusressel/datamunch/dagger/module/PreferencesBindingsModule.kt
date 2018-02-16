package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.preferences.PreferenceActivityBase
import de.markusressel.datamunch.view.activity.preferences.PreferenceOverviewActivity
import de.markusressel.datamunch.view.fragment.preferences.ConnectionPreferences
import de.markusressel.datamunch.view.fragment.preferences.DaggerPreferenceFragment
import de.markusressel.datamunch.view.fragment.preferences.FileUploaderPreferences
import de.markusressel.datamunch.view.fragment.preferences.SecurityPreferences

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
    internal abstract fun ConnectionPreferences(): ConnectionPreferences

    @ContributesAndroidInjector
    internal abstract fun FileUploaderPreferences(): FileUploaderPreferences

    @ContributesAndroidInjector
    internal abstract fun SecurityPreferences(): SecurityPreferences

}