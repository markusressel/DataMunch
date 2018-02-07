package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.annotation.MenuRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

/**
 * Base class for a fragment with an options menu
 *
 * Created by Markus on 31.01.2018.
 */
abstract class OptionsMenuFragmentBase : DaggerSupportFragmentBase() {

    /**
     * The layout resource for this Activity
     */
    @get:MenuRes
    protected abstract val optionsMenuRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        optionsMenuRes
                ?.let {
                    setHasOptionsMenu(true)
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        optionsMenuRes
                ?.let {
                    inflater
                            ?.inflate(it, menu)
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return false
        }

        if (super.onOptionsItemSelected(item)) {
            return true
        }

        return onOptionsMenuItemSelected(item)
    }

    /**
     * Override this if you want to add option menu items to your fragment
     */
    protected open fun onOptionsMenuItemSelected(item: MenuItem): Boolean {
        return false
    }

}