package de.markusressel.datamunch.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.markusressel.datamunch.application.App
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class),
    //(PreferencesModule::class),
    (AndroidInjectionModule::class),
    (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}
