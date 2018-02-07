package de.markusressel.datamunch.application

import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector
import de.markusressel.datamunch.BuildConfig
import de.markusressel.datamunch.dagger.DaggerAppComponent
import timber.log.Timber

/**
 * Created by Markus on 20.12.2017.
 */
class App : DaggerApplication(), HasActivityInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
    }

    override fun onCreate() {
        super
                .onCreate()

        plantTimberTrees()
        initMemoryLeakDetection()
    }

    private fun initMemoryLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary
                .install(this)
    }

    private fun plantTimberTrees() {
        if (BuildConfig.DEBUG) {
            Timber
                    .plant(Timber.DebugTree())
        }
    }

}
