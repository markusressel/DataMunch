package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.*

/**
 * Created by Markus on 07.01.2018.
 */
@Module
abstract class BaseBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun NavigationDrawerActivity(): NavigationDrawerActivity

    @ContributesAndroidInjector
    internal abstract fun DaggerSupportFragmentBase(): DaggerSupportFragmentBase

    @ContributesAndroidInjector
    internal abstract fun LoadingSupportFragmentBase(): LoadingSupportFragmentBase

    @ContributesAndroidInjector
    internal abstract fun ServerStatusFragment(): ServerStatusFragment

    @ContributesAndroidInjector
    internal abstract fun ServicesFragment(): ServicesFragment

    @ContributesAndroidInjector
    internal abstract fun JailsFragment(): JailsFragment

    @ContributesAndroidInjector
    internal abstract fun PluginsFragment(): PluginsFragment

}