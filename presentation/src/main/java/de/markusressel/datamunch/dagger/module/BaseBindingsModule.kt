package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.JailsFragment
import de.markusressel.datamunch.view.fragment.LoadingSupportFragmentBase
import de.markusressel.datamunch.view.fragment.ServerStatusFragment

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
    internal abstract fun JailsFragment(): JailsFragment

}