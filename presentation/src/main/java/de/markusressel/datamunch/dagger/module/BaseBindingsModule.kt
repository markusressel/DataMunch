package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.ServerStatusFragment

/**
 * Created by Markus on 07.01.2018.
 */
@Module
abstract class BaseBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun NavigationDrawerActivity(): NavigationDrawerActivity

    @ContributesAndroidInjector
    internal abstract fun ServerStatusFragment(): ServerStatusFragment

}