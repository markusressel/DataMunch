package de.markusressel.datamunch.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.markusressel.datamunch.application.App
import de.markusressel.datamunch.dagger.module.BaseBindingsModule
import de.markusressel.datamunch.dagger.module.PersistenceBindingsModule
import de.markusressel.datamunch.dagger.module.PreferencesBindingsModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [(AppModule::class), (BaseBindingsModule::class), (PreferencesBindingsModule::class), (PersistenceBindingsModule::class), (AndroidInjectionModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}
