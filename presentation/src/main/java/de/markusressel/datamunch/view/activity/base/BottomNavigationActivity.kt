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

package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

/**
 * Created by Markus on 07.02.2018.
 */
abstract class BottomNavigationActivity : NavigationDrawerActivity() {

    lateinit var bottomNavigation: AHBottomNavigation

    abstract fun getBottomNavigationItems(): List<AHBottomNavigationItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        setBottomBarStyle()
        createBottomNavigation()
    }

    private fun setBottomBarStyle() {
        bottomNavigation
                .accentColor = themeHelper
                .getThemeAttrColor(this, android.R.attr.colorAccent)

        bottomNavigation
                .defaultBackgroundColor = themeHelper
                .getThemeAttrColor(this, android.R.attr.colorPrimary)
    }

    private fun createBottomNavigation() {
        bottomNavigation
                .setOnTabSelectedListener { position, wasSelected ->
                    onBottomNavigationItemSelected(position, wasSelected)
                    true
                }

        getBottomNavigationItems()
                .toObservable()
                .subscribeBy(onNext = {
                    bottomNavigation
                            .addItem(it)
                }, onComplete = {

                }, onError = {
                    Timber
                            .e { "Error adding Bottom Navigation items" }
                })
    }

    /**
     * Called when a bottom navigation item was selected
     */
    abstract fun onBottomNavigationItemSelected(position: Int, wasSelected: Boolean)

}