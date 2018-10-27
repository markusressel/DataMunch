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

package de.markusressel.datamunch.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.utils.PatternLockUtils
import com.andrognito.patternlockview.utils.PatternLockUtils.patternToSha1
import com.andrognito.rxpatternlockview.RxPatternLockView
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.eightbitlab.rxbus.Bus
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.component.LoadingComponent
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

    private var isTouchable = true

    private val loadingComponent by lazy {
        LoadingComponent(this)
    }

    override fun initComponents(context: Context) {
        super
                .initComponents(context)
        loadingComponent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        return loadingComponent
                .onCreateView(inflater, parent, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        loadingComponent
                .showLoading()

        backgroundImageView
                .setImage(ImageSource.resource(R.drawable.lockscreen_background_black_lines))
        backgroundImageView
                .setOnImageEventListener(object : SubsamplingScaleImageView.OnImageEventListener {
                    override fun onImageLoaded() {
                        loadingComponent
                                .showContent(true)

                        fadeView(rootLayout, 0f, 1f)
                    }

                    override fun onReady() {
                    }

                    override fun onTileLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingComponent
                                    .showError(p0)
                        } else {
                            loadingComponent
                                    .showError("Error loading tile")
                        }
                    }

                    override fun onPreviewReleased() {
                    }

                    override fun onImageLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingComponent
                                    .showError(p0)
                        } else {
                            loadingComponent
                                    .showError("Error loading image")
                        }
                    }

                    override fun onPreviewLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingComponent
                                    .showError(p0)
                        } else {
                            loadingComponent
                                    .showError("Error loading preview")
                        }
                    }
                })

        backgroundImageView
                .setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP)

        profileImage
                .setImageDrawable(iconHandler.getIcon(MaterialDesignIconic.Icon.gmi_lock,
                                                      ContextCompat.getColor(context as Context,
                                                                             R.color.md_grey_500),
                                                      48, 0))

        patternLockView
                .setOnTouchListener { _: View, _: MotionEvent ->
                    !isTouchable
                }
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

                            shimmerLayout
                                    .stopShimmerAnimation()

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

                            shimmerLayout
                                    .startShimmerAnimation()
                        }
                    }
                })

        shimmerLayout
                .startShimmerAnimation()
    }

    private fun checkPattern(pattern: List<PatternLockView.Dot>?) {
        // TODO:
        //        val correctPattern = preferencesHolder.lockPattern.persistedValue
        val correctPattern = "bla"
        val patternAsString = patternToSha1(patternLockView, pattern)

        if (patternAsString == correctPattern) {
            Single
                    .fromCallable {
                        patternLockView
                                .setViewMode(PatternLockView.PatternViewMode.CORRECT)
                        isTouchable = false
                    }
                    .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                    .delay(250, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = {
                        unlockScreen()
                        patternLockView
                                .clearPattern()
                        isTouchable = true
                    })
        } else {
            Single
                    .fromCallable {
                        patternLockView
                                .setViewMode(PatternLockView.PatternViewMode.WRONG)
                        isTouchable = false
                    }
                    .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                    .delay(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onSuccess = {
                        patternLockView
                                .clearPattern()
                        isTouchable = true
                    })
        }
    }

    private fun unlockScreen() {
        Bus
                .send(LockEvent(false))
    }

    private fun fadeView(view: View, fromAlpha: Float? = null, toAlpha: Float) {
        val interpolator = when {
            toAlpha > 0 -> DecelerateInterpolator()
            else -> LinearInterpolator()
        }

        val duration = when {
            toAlpha >= 1 -> LoadingComponent.FADE_IN_DURATION_MS
            toAlpha <= 0 -> LoadingComponent.FADE_OUT_DURATION_MS
            else -> LoadingComponent.FADE_DURATION_MS
        }

        view
                .animate()
                .alpha(toAlpha)
                .setDuration(duration)
                .setInterpolator(interpolator)
                .withStartAction {
                    if (toAlpha > 0) {
                        view
                                .alpha = fromAlpha ?: 0f
                        view
                                .visibility = View
                                .VISIBLE
                    }
                }
                .withEndAction {
                    if (toAlpha <= 0) {
                        view
                                .visibility = View
                                .GONE
                    }
                }
    }

}


