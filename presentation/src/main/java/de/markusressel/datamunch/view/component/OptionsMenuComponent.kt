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

package de.markusressel.datamunch.view.component

import android.arch.lifecycle.Lifecycle
import android.support.annotation.MenuRes
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.view.fragment.base.LifecycleFragmentBase
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Markus on 15.02.2018.
 */
class OptionsMenuComponent(
        hostFragment: LifecycleFragmentBase,
        /**
         * The layout resource for this Activity
         */
        @get:MenuRes val optionsMenuRes: Int,
        val onOptionsMenuItemClicked: ((item: MenuItem) -> Boolean)? = null,
        val onCreateOptionsMenu: ((menu: Menu?, inflater: MenuInflater?) -> Unit)? = null) :
    FragmentComponent(hostFragment) {

    init {
        hostFragment
                .lifecycle()
                .filter {
                    setOf(FragmentEvent.CREATE, FragmentEvent.RESUME, FragmentEvent.DESTROY)
                            .contains(it)
                }
                .bindUntilEvent(hostFragment, Lifecycle.Event.ON_DESTROY)
                .subscribeBy(onNext = {
                    when (it) {
                        FragmentEvent.CREATE -> {
                            hostFragment
                                    .setHasOptionsMenu(true)
                        }
                    }
                })
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