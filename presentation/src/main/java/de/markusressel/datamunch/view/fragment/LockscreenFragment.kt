package de.markusressel.datamunch.view.fragment

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
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
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.event.LockEvent
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.plugin.LoadingPlugin
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

    val loadingPlugin = LoadingPlugin()


    init {
        addFragmentPlugins(loadingPlugin)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        loadingPlugin
                .showLoading()

        backgroundImageView
                .setImage(ImageSource.resource(R.drawable.lockscreen_background_black_lines))
        backgroundImageView
                .setOnImageEventListener(object : SubsamplingScaleImageView.OnImageEventListener {
                    override fun onImageLoaded() {
                        loadingPlugin
                                .showContent(true)

                        fadeView(rootLayout, 0f, 1f)
                    }

                    override fun onReady() {
                    }

                    override fun onTileLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingPlugin
                                    .showError(p0)
                        } else {
                            loadingPlugin
                                    .showError("Error loading tile")
                        }
                    }

                    override fun onPreviewReleased() {
                    }

                    override fun onImageLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingPlugin
                                    .showError(p0)
                        } else {
                            loadingPlugin
                                    .showError("Error loading image")
                        }
                    }

                    override fun onPreviewLoadError(p0: java.lang.Exception?) {
                        if (p0 != null) {
                            loadingPlugin
                                    .showError(p0)
                        } else {
                            loadingPlugin
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

    private fun fadeView(view: View, fromAlpha: Float? = null, toAlpha: Float) {
        val interpolator = when {
            toAlpha > 0 -> DecelerateInterpolator()
            else -> LinearInterpolator()
        }

        val duration = when {
            toAlpha >= 1 -> LoadingPlugin.FADE_IN_DURATION_MS
            toAlpha <= 0 -> LoadingPlugin.FADE_OUT_DURATION_MS
            else -> LoadingPlugin.FADE_DURATION_MS
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


