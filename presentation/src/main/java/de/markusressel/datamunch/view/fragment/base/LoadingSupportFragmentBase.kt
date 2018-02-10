package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

        errorLayout
                .setOnClickListener {
                    onErrorClicked()
                }

        return rootView
    }

    protected open fun onErrorClicked() {
        // TODO: Show sophisticated layout_error screen
    }

    private fun createWrapperLayout(): LinearLayout {
        val baseLayout = LinearLayout(activity)
        baseLayout
                .orientation = LinearLayout
                .VERTICAL

        // inflate "layout_loading" and "layout_error" layouts and attach it to a newly created layout
        val layoutInflater = LayoutInflater
                .from(context)
        layoutInflater.inflate(R.layout.layout_loading, baseLayout, true) as ViewGroup
        layoutInflater.inflate(R.layout.layout_error, baseLayout, true) as ViewGroup

        // attach the original content view
        baseLayout
                .addView(contentView)

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
        loadingLayout
                .visibility = View
                .VISIBLE
        errorLayout
                .visibility = View
                .GONE
        contentView
                .visibility = View
                .GONE
    }

    /**
     * Show the actual page content
     */
    @CallSuper
    protected open fun showContent() {
        loadingLayout
                .visibility = View
                .GONE
        errorLayout
                .visibility = View
                .GONE
        contentView
                .visibility = View
                .VISIBLE
    }

    /**
     * Show an layout_error screen
     *
     * @param message the message to show
     */
    protected fun showError(message: String) {
        showError(message, null)
    }

    /**
     * Show an layout_error screen
     *
     * @param throwable the exception that was raised
     */
    protected fun showError(throwable: Throwable) {
        showError("Exception raised", throwable)
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

    private fun showError(message: String, t: Throwable? = null) {
        var errorDescriptionText = message

        t
                ?.let {
                    Timber
                            .e(t) { message }
                    errorDescriptionText += "\n" + t.prettyPrint()
                }

        errorDescription
                .text = errorDescriptionText

        loadingLayout
                .visibility = View
                .GONE
        errorLayout
                .visibility = View
                .VISIBLE
        contentView
                .visibility = View
                .GONE

        onShowError(message, t)
    }

}