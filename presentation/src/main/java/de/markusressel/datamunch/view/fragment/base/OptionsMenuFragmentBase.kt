package de.markusressel.datamunch.view.fragment.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.MenuRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R

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

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        optionsMenuRes
                ?.let {
                    setHasOptionsMenu(true)
                }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        optionsMenuRes
                ?.let {
                    inflater
                            ?.inflate(it, menu)

                    // set refresh icon
                    val refreshIcon = iconHandler
                            .getOptionsMenuIcon(MaterialDesignIconic.Icon.gmi_refresh)
                    menu
                            ?.findItem(R.id.refresh)
                            ?.icon = refreshIcon
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