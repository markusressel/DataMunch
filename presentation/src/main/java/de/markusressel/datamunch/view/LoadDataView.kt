package de.markusressel.datamunch.view

import android.content.Context

/**
 * Interface representing a View that will use to load data.
 *
 * Created by Markus on 09.01.2018.
 */
interface LoadDataView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    fun showLoading()

    /**
     * Hide a loading view.
     */
    fun hideLoading()

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    fun showRetry()

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    fun hideRetry()

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    fun showError(message: String)

    /**
     * Get a [android.content.Context].
     */
    fun context(): Context

}