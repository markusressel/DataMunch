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

package de.markusressel.datamunch.view.component

import android.arch.lifecycle.Lifecycle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.LockscreenFragment
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Markus on 15.02.2018.
 */
class LockComponent(hostActivity: LifecycleProvider<ActivityEvent>,
                    val preferenceHandler: () -> PreferenceHandler) :
    ActivityComponent(hostActivity) {

    private lateinit var lockLayout: ViewGroup
    private lateinit var originalLayout: View

    init {
        lifecycleProvider
                .lifecycle()
                .filter {
                    setOf(ActivityEvent.CREATE, ActivityEvent.RESUME, ActivityEvent.DESTROY)
                            .contains(it)
                }
                .bindUntilEvent(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
                .subscribeBy(onNext = {
                    when (it) {
                        ActivityEvent.CREATE -> {
                            if (!lockInitialized) {
                                isScreenLocked = isLockEnabled()
                                lockInitialized = true
                            }

                            Bus
                                    .observe<LockEvent>()
                                    .subscribe {
                                        setScreenLock(it.lock)
                                    }
                                    .registerInBus(this)
                        }

                        ActivityEvent.RESUME -> {
                            setScreenLock(isLockEnabled() && isScreenLocked)
                        }

                        ActivityEvent.DESTROY -> {
                            Timber
                                    .d { "DESTROY EVENT IN LOCK COMPONENT" }
                        }
                    }
                })
    }

    private fun isLockEnabled(): Boolean {
        val useLock = preferenceHandler()
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        val pattern = preferenceHandler()
                .getValue(PreferenceHandler.LOCK_PATTERN)

        return useLock && pattern.isNotEmpty()
    }

    fun setContentView(view: View?): View? {
        val contentView: View?
        if (view != null) {
            val wrapperLayout = createWrapperLayout(view)
            contentView = wrapperLayout
//            view.parent?.let {
//                (it as ViewGroup).addView(contentView)
//            }
        } else {
            Timber
                    .e { "LockPlugin couldn't attach to the parent view as it was NULL" }
            contentView = view
        }

        return contentView
    }

    private fun createWrapperLayout(view: View): ViewGroup {
        val baseLayout = FrameLayout(activity)

        // remove original parent if it exists
        view.parent?.let {
            val childParent = view.parent as ViewGroup
            childParent.removeView(view)
        }

        originalLayout = view
        // hide initially to unlock it later
        originalLayout
                .visibility = View
                .GONE

        // attach the original content view
        baseLayout
                .addView(view, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                        FrameLayout.LayoutParams.MATCH_PARENT))
        // create content container
        lockLayout = FrameLayout(activity)
        lockLayout
                .id = R
                .id
                .lockContentLayout
        baseLayout
                .addView(lockLayout, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                              FrameLayout.LayoutParams.MATCH_PARENT))

        activity
                .supportFragmentManager
                .beginTransaction()
                .replace(lockLayout.id, LockscreenFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commit()

        return baseLayout
    }

    private fun setScreenLock(locked: Boolean) {
        Bus
                .send(SetStatusBarStateEvent(!locked))
        if (locked) {
            lockScreen()
        } else {
            unlockScreen()
        }
    }

    data class SetStatusBarStateEvent(val visible: Boolean)

    /**
     * Locks the screen if enabled in preferences
     * Otherwise this is a no-op
     */
    private fun lockScreen() {
        activity
                .runOnUiThread {
                    originalLayout
                            .visibility = View
                            .GONE
                    lockLayout
                            .visibility = View
                            .VISIBLE
                }

        isScreenLocked = true
    }

    /**
     * Unlocks the screen
     */
    private fun unlockScreen() {
        activity
                .runOnUiThread {
                    originalLayout
                            .visibility = View
                            .VISIBLE
                    lockLayout
                            .visibility = View
                            .GONE
                }

        isScreenLocked = false
    }

    fun onDestroy() {
        //unsubscribe from events
        Bus
                .unregister(this)
    }

    companion object {

        /**
         * Indicates whether the screen lock's initial state has been initialized
         */
        @Volatile
        var lockInitialized = false

        /**
         * Indicates whether the screen is locked
         */
        @Volatile
        var isScreenLocked = false
    }
}