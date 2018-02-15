package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.support.annotation.CallSuper
import android.util.Log
import android.view.View
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.andrognito.rxpatternlockview.RxPatternLockView
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent
import com.eightbitlab.rxbus.Bus
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_lockscreen.*


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class LockscreenFragment : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_lockscreen

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        profileImage
                .setImageDrawable(iconHandler.getWizardIcon(MaterialDesignIconic.Icon.gmi_lock))

        RxPatternLockView
                .patternChanges(patternLockView)
                .subscribe(object : Consumer<PatternLockCompoundEvent> {
                    @Throws(Exception::class)
                    override fun accept(event: PatternLockCompoundEvent) {
                        if (event.eventType == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log
                                    .d(javaClass.name, "Pattern drawing started")
                        } else if (event.eventType == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {
                            Log
                                    .d(javaClass.name,
                                       "Pattern progress: " + PatternLockUtils.patternToString(
                                               patternLockView, event.pattern))
                        } else if (event.eventType == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            Log
                                    .d(javaClass.name,
                                       "Pattern complete: " + PatternLockUtils.patternToString(
                                               patternLockView, event.pattern))

                            unlockScreen()
                        } else if (event.eventType == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log
                                    .d(javaClass.name, "Pattern has been cleared")
                        }
                    }
                })

    }

    private fun lockScreen() {
        Bus
                .send(LockEvent(true))
    }

    private fun unlockScreen() {
        Bus
                .send(LockEvent(false))
    }

}