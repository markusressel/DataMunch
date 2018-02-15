package de.markusressel.datamunch.view.plugin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.pascalwelsch.compositeandroid.activity.ActivityPlugin
import de.markusressel.datamunch.R
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.LockscreenFragment

/**
 * Created by Markus on 15.02.2018.
 */
class LockPlugin(val contentFragment: () -> Fragment) : ActivityPlugin() {

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

        val fragment: Fragment = when (isLocked) {
            true -> LockscreenFragment()
            false -> contentFragment()
        }

        original
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, fragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()
    }

    private fun lockScreen() {
        original
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, LockscreenFragment())
                //                .addToBackStack(preferencesFragment.tag)
                .commitAllowingStateLoss()
    }

    private fun unlockScreen() {
        original
                .supportFragmentManager
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
        private var isLocked = true
    }

}