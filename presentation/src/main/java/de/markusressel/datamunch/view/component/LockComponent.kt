package de.markusressel.datamunch.view.component

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.LockscreenFragment

/**
 * Created by Markus on 15.02.2018.
 */
class LockComponent(hostActivity: AppCompatActivity,
                    val preferenceHandler: () -> PreferenceHandler) :
    ActivityComponent(hostActivity) {

    private lateinit var lockLayout: ViewGroup
    private lateinit var originalLayout: View

    fun setContentView(view: View?): View? {
        val contentView: View?
        if (view != null) {
            val wrapperLayout = createWrapperLayout(view)
            contentView = wrapperLayout
        } else {
            Timber
                    .e { "LockPlugin couldn't attach to the parent view as it was NULL" }
            contentView = view
        }

        return contentView
    }

    private fun createWrapperLayout(view: View): ViewGroup {
        val baseLayout = FrameLayout(hostActivity)

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
        lockLayout = FrameLayout(hostActivity)
        lockLayout
                .id = R
                .id
                .lockContentLayout
        baseLayout
                .addView(lockLayout, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                                              FrameLayout.LayoutParams.MATCH_PARENT))

        hostActivity
                .supportFragmentManager
                .beginTransaction()
                .replace(lockLayout.id, LockscreenFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commit()

        return baseLayout
    }

    fun onCreate(savedInstanceState: Bundle?) {
        Bus
                .observe<LockEvent>()
                .subscribe {
                    setScreenLock(it.lock)
                }
                .registerInBus(this)
    }

    fun onResume() {
        val useLock = preferenceHandler()
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        val pattern = preferenceHandler()
                .getValue(PreferenceHandler.LOCK_PATTERN)

        setScreenLock(useLock && pattern.isNotEmpty() && isLocked)
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
        hostActivity
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

    /**
     * Unlocks the screen
     */
    private fun unlockScreen() {
        hostActivity
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

    fun onDestroy() {
        //unsubscribe from events
        Bus
                .unregister(this)
    }

    companion object {
        /**
         * Indicates whether the screen is locked
         */
        @Volatile
        var isLocked = false
    }
}