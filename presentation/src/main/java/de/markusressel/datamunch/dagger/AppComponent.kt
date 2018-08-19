/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import de.markusressel.datamunch.application.App
import de.markusressel.datamunch.dagger.module.BaseBindingsModule
import de.markusressel.datamunch.dagger.module.PersistenceBindingsModule
import de.markusressel.datamunch.dagger.module.PreferencesBindingsModule
import de.markusressel.datamunch.dagger.module.RestBindingsModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [(AppModule::class), (BaseBindingsModule::class), (RestBindingsModule::class), (PreferencesBindingsModule::class), (PersistenceBindingsModule::class), (AndroidInjectionModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}
