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
    public override fun onSaveInstanceState(outState: Bundle) {
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

        // instantiate manually since CompositeAndroid cant access injected objects
        val preferenceHandler = PreferenceHandler(original)
        val usePattern = preferenceHandler
                .getValue(PreferenceHandler.USE_PATTERN_LOCK)
        if (usePattern) {
            lockScreen()
        } else {
            unlockScreen()
        }

        return baseLayout
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
    }

    private fun lockScreen() {
        originalLayout
                .visibility = View
                .GONE
        lockLayout
                .visibility = View
                .VISIBLE
    }

    private fun unlockScreen() {
        originalLayout
                .visibility = View
                .VISIBLE
        lockLayout
                .visibility = View
                .GONE
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
        private var isLocked = true
    }

}