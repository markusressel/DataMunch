package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.fragment.JailsFragment
import de.markusressel.datamunch.view.fragment.PluginsFragment
import de.markusressel.datamunch.view.fragment.ServerStatusFragment
import de.markusressel.datamunch.view.fragment.ServicesFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.LoadingSupportFragmentBase

/**
 * Created by Markus on 07.01.2018.
 */
@Module
abstract class BaseBindingsModule {

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