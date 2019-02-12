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

package de.markusressel.datamunch.application

import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.markusressel.datamunch.BuildConfig
import de.markusressel.datamunch.dagger.DaggerAppComponent
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.preferences.KutePreferencesHolder
import de.markusressel.datamunch.ssh.ConnectionManager
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Markus on 20.12.2017.
 */
class App : ExceptionHandlerApplicationBase() {

    @Inject
    protected lateinit var preferenceHolder: KutePreferencesHolder

    @Inject
    protected lateinit var connectionManager: ConnectionManager

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
    }

    override fun onCreate() {
        super
                .onCreate()
        // register app lifecycle
        registerActivityLifecycleCallbacks(AppLifecycleTracker(preferenceHolder))

        // Clear DB entirely
        //        BoxStore
        //                .deleteAllFiles(applicationContext, null)

        // TODO: Remove when preference is implemented
        //        // set default pattern
        // TODO: implement KutePatternPreference
        //        preferenceHolder.lockPattern.persis
        //                .setValue(PreferenceHandler.LOCK_PATTERN,
        //                          "cb69e3a54154e27cad0c566f520742c2645847c1")

        plantTimberTrees()
        initMemoryLeakDetection()

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())
    }

    private fun initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    private fun plantTimberTrees() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
