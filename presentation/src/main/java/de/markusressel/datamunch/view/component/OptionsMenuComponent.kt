package de.markusressel.datamunch.view.component

import android.os.Bundle
import android.support.annotation.MenuRes
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

/**
 * Created by Markus on 15.02.2018.
 */
class OptionsMenuComponent(
        hostFragment: Fragment,
        /**
         * The layout resource for this Activity
         */
        @get:MenuRes val optionsMenuRes: Int,
        val onOptionsMenuItemClicked: ((item: MenuItem) -> Boolean)? = null,
        val onCreateOptionsMenu: ((menu: Menu?, inflater: MenuInflater?) -> Unit)? = null) :
    FragmentComponent(hostFragment) {

    fun afterOnCreate(savedInstanceState: Bundle?) {
        hostFragment
                .setHasOptionsMenu(true)
    }

    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater
                ?.inflate(optionsMenuRes, menu)

        onCreateOptionsMenu
                ?.let {
                    it(menu, inflater)
                }
    }

    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) {
            return false
        }

        onOptionsMenuItemClicked
                ?.let {
                    return it(item)
                }

        return false
    }

}