package de.markusressel.datamunch.application;

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.eightbitlab.rxbus.Bus
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.plugin.LockPlugin

class AppLifecycleTracker(val preferenceHandler: PreferenceHandler) :
    Application.ActivityLifecycleCallbacks {

    private var numStarted = 0

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        if (numStarted == 0) {
            // app went to foreground
            onAppForeground()
        }
        numStarted++
    }

    private fun onAppForeground() {

    }

    override fun onActivityResumed(p0: Activity?) {
    }

    override fun onActivityPaused(p0: Activity?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        numStarted--
        if (numStarted == 0) {
            // app went to background
            onAppBackground()
        }
    }

    private fun onAppBackground() {
        val useLock = preferenceHandler
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        if (useLock) {
            LockPlugin
                    .isLocked = true
            // in case anyone is still listening
            Bus
                    .send(LockEvent(true))
        }
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

}