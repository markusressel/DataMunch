package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import kotlinx.android.synthetic.main.layout_error.*

/**
 * Base class for implementing a fragment
 *
 * Created by Markus on 07.01.2018.
 */
abstract class LoadingSupportFragmentBase : OptionsMenuFragmentBase() {

    protected lateinit var loadingLayout: ViewGroup
    protected lateinit var errorLayout: ViewGroup
    protected lateinit var contentView: ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        rootView = createWrapperLayout()
        loadingLayout = rootView
                .findViewById(R.id.layoutLoading)
        errorLayout = rootView
                .findViewById(R.id.layoutError)

        return rootView
    }

    private fun createWrapperLayout(): ViewGroup {
        val baseLayout = FrameLayout(activity)

        // attach the original content view
        baseLayout
                .addView(contentView)

        // inflate "layout_loading" and "layout_error" layouts and attach it to a newly created layout
        val layoutInflater = LayoutInflater
                .from(context)
        layoutInflater.inflate(R.layout.layout_error, baseLayout, true) as ViewGroup
        layoutInflater.inflate(R.layout.layout_loading, baseLayout, true) as ViewGroup

        return baseLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        showLoading()
    }

    /**
     * Show layout_loading animation
     */
    @CallSuper
    protected open fun showLoading() {
        fadeView(loadingLayout, 1f)
    }

    /**
     * Show the actual page content
     */
    @CallSuper
    protected open fun showContent() {
        setViewVisibility(errorLayout, View.GONE)
        fadeView(contentView, 1f)
        fadeView(loadingLayout, 0f)
    }

    /**
     * Show an error screen
     *
     * @param message the message to show
     */
    protected fun showError(@StringRes message: Int) {
        showError(getString(message))
    }

    /**
     * Show an error screen
     *
     * @param message the message to show
     */
    protected fun showError(message: String) {
        showError(message, null)
    }

    /**
     * Show an error screen
     *
     * @param throwable the exception that was raised
     */
    protected fun showError(throwable: Throwable) {
        showError(R.string.exception_raised, throwable)
    }

    private fun showError(@StringRes message: Int, throwable: Throwable? = null) {
        showError(getString(message), throwable)
    }

    private fun showError(message: String, throwable: Throwable? = null) {
        throwable
                ?.let {
                    Timber
                            .e(throwable) { message }
                }
        val errorDescriptionText: CharSequence = throwable?.let {
            var text = ""

            if (message.isNotEmpty()) {
                text += "$message\n\n"
            }

            text += "${throwable.javaClass.simpleName}\n"

            throwable
                    .message
                    ?.let {
                        if (it.isNotEmpty()) text += it
                    }

            text
        }
                ?: message

        errorDescription
                .text = errorDescriptionText

        errorLayout
                .setOnClickListener {
                    onErrorClicked(message, throwable)
                }

        setViewVisibility(errorLayout, View.VISIBLE)
        fadeView(contentView, 0f)
        fadeView(loadingLayout, 0f)

        onShowError(message, throwable)
    }

    private fun Throwable.prettyPrint(): String {
        val message = "${this.message}:\n" + "${this.stackTrace.joinToString(separator = "\n")}}"

        return message
    }

    /**
     * Called when the error screen is shown
     */
    protected open fun onShowError(message: String, t: Throwable? = null) {
    }

    /**
     * Called when the error is clicked
     * Show a sophisticated error screen here
     */
    protected open fun onErrorClicked(message: String, t: Throwable?) {
        val contentText = t?.let {
            message + "\n\n\n" + t.prettyPrint()
        }
                ?: message

        MaterialDialog
                .Builder(context as Context)
                .title(R.string.error)
                .content(contentText)
                .positiveText(android.R.string.ok)
                .show()
    }

    private fun setViewVisibility(view: View, visibility: Int) {
        view
                .visibility = visibility
    }

    private fun fadeView(view: View, alpha: Float) {
        val interpolator = when {
            alpha > 0 -> DecelerateInterpolator()
            else -> LinearInterpolator()
        }

        val duration = when {
            alpha >= 1 -> FADE_IN_DURATION_MS
            alpha <= 0 -> FADE_OUT_DURATION_MS
            else -> FADE_DURATION_MS
        }

        view
                .animate()
                .alpha(alpha)
                .setDuration(duration)
                .setInterpolator(interpolator)
                .withStartAction {
                    if (alpha > 0) {
                        view
                                .alpha = 0f
                        view
                                .visibility = View
                                .VISIBLE
                    }
                }
                .withEndAction {
                    if (alpha <= 0) {
                        view
                                .visibility = View
                                .GONE
                    }
                }
    }

    companion object {
        const val FADE_DURATION_MS = 300L
        const val FADE_IN_DURATION_MS = 400L
        const val FADE_OUT_DURATION_MS = FADE_IN_DURATION_MS / 2
    }

}
