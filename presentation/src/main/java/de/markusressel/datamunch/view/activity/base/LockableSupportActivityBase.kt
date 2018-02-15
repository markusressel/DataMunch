/*
 *  PowerSwitch by Max Rosin & Markus Ressel
 *  Copyright (C) 2015  Markus Ressel
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

package de.markusressel.datamunch.view.activity.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import de.markusressel.datamunch.R
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.LockscreenFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import kotlin.reflect.KFunction0

/**
 * Created by Markus on 20.12.2017.
 */
abstract class LockableSupportActivityBase : DaggerSupportActivityBase() {

    override val style: Int
        get() = FULLSCREEN

    /**
     * The content fragment for this Activity
     */
    protected abstract val contentFragment: KFunction0<DaggerSupportFragmentBase>

    @SuppressLint("MissingSuperCall")
    public override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        Bus
                .observe<LockEvent>()
                .subscribe {
                    if (it.lock) {
                        lockScreen()
                        isLocked = true
                    } else {
                        unlockScreen()
                        isLocked = false
                    }
                }
                .registerInBus(this)

        val fragment: DaggerSupportFragmentBase = when (isLocked) {
            true -> LockscreenFragment()
            false -> contentFragment()
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, fragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()
    }

    private fun lockScreen() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, LockscreenFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()
    }

    private fun unlockScreen() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, contentFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        //unsubscribe from events
        Bus
                .unregister(this)

        super
                .onDestroy()
    }

    companion object {
        /**
         * Indicates whether the screen is locked
         */
        @Volatile
        private var isLocked = false
    }

}
