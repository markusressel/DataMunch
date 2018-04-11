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

package de.markusressel.datamunch.application;

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.eightbitlab.rxbus.Bus
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.component.LockComponent

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
            LockComponent
                    .isScreenLocked = true
            // in case anyone is still listening
            Bus
                    .send(LockEvent(true))
        }
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

}