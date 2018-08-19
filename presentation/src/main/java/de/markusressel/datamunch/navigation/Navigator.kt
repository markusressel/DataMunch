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

package de.markusressel.datamunch.navigation

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.aboutlibraries.util.Colors
import com.mikepenz.materialdrawer.Drawer
import de.markusressel.datamunch.R
import de.markusressel.datamunch.preferences.KutePreferencesHolder
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 07.01.2018.
 */
@Singleton
class Navigator @Inject constructor(
        private val preferencesHolder: KutePreferencesHolder

) {

    private val stateStack: Stack<NavigationState> = Stack()

    val currentState: NavigationState
        get() {
            return if (stateStack.isEmpty()) {
                NavigationState(INITIAL_PAGE, NavigationPageHolder.Status)
            } else {
                stateStack
                        .peek()
            }
        }

    lateinit var activity: AppCompatActivity
    lateinit var drawer: Drawer

    /**
     * Navigate to a specific page
     *
     * @param activityContext activity context
     * @param page the page to navigate to
     */
    fun startActivity(activityContext: Context, page: NavigationPage) {
        startActivity(activityContext, page, null)
    }

    /**
     * Navigate to a specific page
     */
    fun navigateTo(drawerMenuItem: DrawerMenuItem): String {
        val newFragment: Fragment

        // page tag HAS to be set
        drawerMenuItem.navigationPage.tag!!

        // initiate transaction
        var transaction: FragmentTransaction = activity
                .supportFragmentManager
                .beginTransaction()

        // try to find previous fragment
        //        if (lastPageTag != null && lastPageTag == page.tag) {
        //            newFragment = activityContext
        //                    .supportFragmentManager
        //                    .findFragmentById(R.id.contentLayout)
        //        } else {
        newFragment = drawerMenuItem.navigationPage.fragment!!()
        //        }

        if (stateStack.isNotEmpty()) {
            transaction = transaction
                    .addToBackStack(null)
        }

        transaction
                .replace(R.id.contentLayout, newFragment, drawerMenuItem.navigationPage.tag)
                .commit()
        activity
                .supportFragmentManager
                .executePendingTransactions()

        activity
                .setTitle(drawerMenuItem.title)
        drawer
                .setSelection(drawerMenuItem.identifier, false)

        // remember page stack
        val newState = NavigationState(drawerMenuItem, drawerMenuItem.navigationPage)
        stateStack
                .push(newState)

        return drawerMenuItem
                .navigationPage
                .tag
    }

    /**
     * Navigate to a specific page using the passed in flags
     *
     * @param activityContext activity context
     * @param page the page to navigate to
     * @param flags Intent flags
     */
    fun startActivity(activityContext: Context, page: NavigationPage, flags: Int?) {
        if (page == NavigationPages.About) {
            navigateToAbout(activityContext)
            return
        }

        val intent = page
                .createIntent(activityContext)
        flags
                ?.let {
                    intent
                            .addFlags(it)
                }

        activityContext
                .startActivity(intent)
    }

    private fun navigateToAbout(activityContext: Context) {
        val themeVal = preferencesHolder
                .themePreference
                .persistedValue

        val aboutLibTheme: Libs.ActivityStyle
        aboutLibTheme = if (themeVal == activityContext.getString(R.string.theme_light_value)) {
            Libs
                    .ActivityStyle
                    .LIGHT_DARK_TOOLBAR
        } else {
            Libs
                    .ActivityStyle
                    .DARK
        }

        LibsBuilder()
                .withActivityStyle(aboutLibTheme)
                .withActivityColor(
                        Colors(ContextCompat.getColor(activityContext, R.color.primaryColor),
                               ContextCompat.getColor(activityContext, R.color.primaryColor_dark)))
                .withActivityTitle(activityContext.getString(R.string.menu_item_about))
                .start(activityContext)
    }

    /**
     * Navigate back to the previous page
     *
     * @return the page that is navigated back to, null if backstack is empty
     */
    fun navigateBack(): NavigationState? {
        if (stateStack.isEmpty()) {
            return null
        }

        // remove current state
        stateStack
                .pop()

        // navigate to previous one if there is one
        if (stateStack.isNotEmpty()) {
            val previousState = stateStack
                    .peek()

            activity
                    .supportFragmentManager
                    .popBackStack()

            activity
                    .setTitle(previousState.drawerMenuItem.title)
            drawer
                    .setSelection(previousState.drawerMenuItem.identifier, false)

            return previousState
        }

        return null
    }

    fun initDrawer() {
        navigateTo(currentState.drawerMenuItem)
    }

    companion object {
        val DrawerItems = DrawerItemHolder
        val NavigationPages = NavigationPageHolder

        val INITIAL_PAGE = DrawerItemHolder
                .Status
    }

}