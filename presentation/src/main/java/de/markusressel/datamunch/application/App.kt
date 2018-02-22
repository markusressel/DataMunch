package de.markusressel.datamunch.application

import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import de.markusressel.datamunch.BuildConfig
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Markus on 20.12.2017.
 */
class App : ExceptionHandlerApplicationBase() {

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
    }

    override fun onCreate() {
        super
                .onCreate()
        // register app lifecycle
        registerActivityLifecycleCallbacks(AppLifecycleTracker(preferenceHandler))

        // Clear DB entirely
        //        BoxStore
        //                .deleteAllFiles(applicationContext, null)

        // TODO: Remove when preference is implemented
        // set default pattern
        preferenceHandler
                .setValue(PreferenceHandler.LOCK_PATTERN,
                          "cb69e3a54154e27cad0c566f520742c2645847c1")

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
