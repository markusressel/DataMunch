package de.markusressel.datamunch.view.fragment

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.CallSuper
import android.util.Log
import android.view.View
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.andrognito.patternlockview.utils.PatternLockUtils.patternToSha1
import com.andrognito.rxpatternlockview.RxPatternLockView
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent
import com.eightbitlab.rxbus.Bus
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_lockscreen.*
import java.util.concurrent.TimeUnit


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

    }

    override fun onResume() {
        super
                .onResume()

        RxPatternLockView
                .patternChanges(patternLockView)
                .bindToLifecycle(patternLockView)
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

                            checkPattern(event.pattern)
                        } else if (event.eventType == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log
                                    .d(javaClass.name, "Pattern has been cleared")
                        }
                    }
                })
    }

    private fun checkPattern(pattern: List<PatternLockView.Dot>?) {
        val correctPattern = preferenceHandler
                .getValue(PreferenceHandler.LOCK_PATTERN)
        val patternAsString = patternToSha1(patternLockView, pattern)

        // TODO: remove true!
        if (true || patternAsString == correctPattern) {
            Single
                    .fromCallable {
                        patternLockView
                                .setViewMode(PatternLockView.PatternViewMode.CORRECT)
                    }
                    .bindUntilEvent(this, Lifecycle.Event.ON_PAUSE)
                    .delay(250, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = {
                        unlockScreen()
                        patternLockView
                                .clearPattern()
                    })
        } else {
            Single
                    .fromCallable {
                        patternLockView
                                .setViewMode(PatternLockView.PatternViewMode.WRONG)
                        // TODO: disable touch input
                    }
                    .bindUntilEvent(this, Lifecycle.Event.ON_PAUSE)
                    .delay(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = {
                        patternLockView
                                .clearPattern()
                        // TODO: enable touch input
                    })
        }
    }

    private fun unlockScreen() {
        Bus
                .send(LockEvent(false))
    }

}