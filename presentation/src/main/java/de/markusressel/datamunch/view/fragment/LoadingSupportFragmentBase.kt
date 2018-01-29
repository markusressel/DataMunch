package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import de.markusressel.datamunch.R

/**
 * Base class for implementing a fragment
 *
 * Created by Markus on 07.01.2018.
 */
abstract class LoadingSupportFragmentBase : DaggerSupportFragmentBase() {

    protected lateinit var loadingLayout: ViewGroup
    protected lateinit var errorLayout: ViewGroup
    protected lateinit var contentView: ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup

        rootView = createWrapperLayout()

        errorLayout.setOnClickListener {
            // TODO: Show sophisticated error screen
        }

        return rootView
    }

    private fun createWrapperLayout(): LinearLayout {
        val baseLayout = LinearLayout(activity)
        baseLayout.orientation = LinearLayout.VERTICAL

        // inflate "loading" and "error" layouts and attach it to a newly created layout
        val layoutInflater = LayoutInflater.from(context)
        loadingLayout = layoutInflater.inflate(R.layout.loading, baseLayout, true) as ViewGroup
        errorLayout = layoutInflater.inflate(R.layout.error, baseLayout, true) as ViewGroup

        // attach the original content view
        baseLayout.addView(contentView)

        return baseLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()
    }

    /**
     * Show loading animation
     */
    protected fun showLoading() {
        loadingLayout.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        contentView.visibility = View.GONE
    }

    /**
     * Show the actual page content
     */
    protected fun showContent() {
        loadingLayout.visibility = View.GONE
        errorLayout.visibility = View.GONE
        contentView.visibility = View.VISIBLE
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
        showError("Exception raised", throwable)
    }

    private fun showError(message: String, t: Throwable? = null) {
        loadingLayout.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        contentView.visibility = View.GONE
    }

}