package de.markusressel.datamunch.view.plugin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.MenuRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.pascalwelsch.compositeandroid.fragment.FragmentPlugin

/**
 * Created by Markus on 15.02.2018.
 */
class OptionsMenuPlugin(
        /**
         * The layout resource for this Activity
         */
        @get:MenuRes val optionsMenuRes: Int,
        val onOptionsMenuItemClicked: ((item: MenuItem) -> Boolean)? = null,
        val onCreateOptionsMenu: ((menu: Menu?, inflater: MenuInflater?) -> Unit)? = null) :
    FragmentPlugin() {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        original
                .setHasOptionsMenu(true)
    }

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater
                ?.inflate(optionsMenuRes, menu)

        onCreateOptionsMenu
                ?.let {
                    it(menu, inflater)
                }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return false
        }

        if (super.onOptionsItemSelected(item)) {
            return true
        }

        onOptionsMenuItemClicked
                ?.let {
                    return it(item)
                }

        return false
    }

}