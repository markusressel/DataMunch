package de.markusressel.datamunch.view.plugin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import com.pascalwelsch.compositeandroid.activity.ActivityPlugin
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.LockscreenFragment

/**
 * Created by Markus on 15.02.2018.
 */
class LockPlugin : ActivityPlugin() {

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun setContentView(view: View?) {
        if (view != null) {
            val wrapperLayout = createWrapperLayout(view)
            original
                    .delegate
                    .setContentView(wrapperLayout)
        } else {
            Timber
                    .e { "LockPlugin coulnd't attach to the parent view as it was NULL" }
            original
                    .delegate
                    .setContentView(view)
        }
    }

    private lateinit var lockLayout: ViewGroup
    private lateinit var originalLayout: View

    // instantiate manually since CompositeAndroid cant access injected objects
    val preferenceHandler by lazy {
        PreferenceHandler(original)
    }

    private fun createWrapperLayout(view: View): ViewGroup {
        val baseLayout = FrameLayout(original)

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
        lockLayout = FrameLayout(original)
        lockLayout
                .id = R
                .id
                .lockContentLayout
        baseLayout
                .addView(lockLayout, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                              FrameLayout.LayoutParams.MATCH_PARENT))

        original
                .supportFragmentManager
                .beginTransaction()
                .replace(lockLayout.id, LockscreenFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()

        // lock on create if enabled
        val useLock = preferenceHandler
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        setScreenLock(useLock)

        return baseLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        Bus
                .observe<LockEvent>()
                .subscribe {
                    setScreenLock(it.lock)
                }
                .registerInBus(this)
    }

    override fun onResume() {
        super
                .onResume()
        setScreenLock(isLocked)
    }

    private fun setScreenLock(locked: Boolean) {
        if (locked) {
            lockScreen()
        } else {
            unlockScreen()
        }
    }

    /**
     * Locks the screen if enabled in preferences
     * Otherwise this is a no-op
     */
    private fun lockScreen() {
        val useLock = preferenceHandler
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        if (useLock) {
            original
                    .runOnUiThread {
                        originalLayout
                                .visibility = View
                                .GONE
                        lockLayout
                                .visibility = View
                                .VISIBLE
                    }

            isLocked = true
        }
    }

    /**
     * Unlocks the screen
     */
    private fun unlockScreen() {
        original
                .runOnUiThread {
                    originalLayout
                            .visibility = View
                            .VISIBLE
                    lockLayout
                            .visibility = View
                            .GONE
                }

        isLocked = false
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
        var isLocked = true
    }
}