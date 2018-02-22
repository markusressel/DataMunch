package de.markusressel.datamunch.dagger

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.application.App
import de.markusressel.datamunch.view.activity.MainActivity
import de.markusressel.datamunch.view.activity.UnknownErrorDialog
import de.markusressel.datamunch.view.activity.base.BottomNavigationActivity
import de.markusressel.datamunch.view.activity.base.DaggerSupportActivityBase
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Module
abstract class AppModule {

    @Binds
    internal abstract fun application(application: App): Application

    @ContributesAndroidInjector
    internal abstract fun DaggerSupportActivityBase(): DaggerSupportActivityBase

    @ContributesAndroidInjector
    internal abstract fun NavigationDrawerActivity(): NavigationDrawerActivity

    @ContributesAndroidInjector
    internal abstract fun BottomNavigationActivity(): BottomNavigationActivity

    @ContributesAndroidInjector
    internal abstract fun MainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun UnknownErrorDialog(): UnknownErrorDialog

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideContext(application: Application): Context {
            return application
        }

    }

}
