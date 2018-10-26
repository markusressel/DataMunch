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

package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

/**
 * Created by Markus on 14.02.2018.
 */
abstract class TabNavigationFragment : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_tab_navigation

    private lateinit var tabNavigation: NavigationTabStrip

    private lateinit var viewPager: ViewPager

    private var currentPage: Int by savedInstanceState(0)

    abstract val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager) as ViewPager
        tabNavigation = view.findViewById(R.id.tabBar) as NavigationTabStrip

        setupViewPager()

        // set initial page
        viewPager
                .currentItem = currentPage
    }

    private fun setupViewPager() {
        viewPager
                .adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                // get fragment and create a new instance
                return tabItems[position]
                        .second()
            }

            override fun getCount(): Int {
                return tabItems
                        .size
            }
        }

        viewPager
                .offscreenPageLimit = tabItems
                .size

        viewPager
                .addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) = Unit

                    override fun onPageScrolled(position: Int, positionOffset: Float,
                                                positionOffsetPixels: Int) = Unit

                    override fun onPageSelected(position: Int) {
                        currentPage = position
                    }

                })
    }

    override fun onResume() {
        super
                .onResume()

        createTabBar()
    }

    private fun createTabBar() {
        // set tab titles
        Single
                .fromObservable(tabItems.toObservable().map {
                    // convert from StringRes to String
                    getString(it.first)
                }.toList().toObservable())
                .map {
                    it
                            .toTypedArray()
                }
                .bindToLifecycle(this)
                .subscribeBy(onSuccess = {
                    tabNavigation
                            .setTitles(*it)
                }, onError = {
                    Timber
                            .e { "Error adding Tab Navigation items" }
                })

        tabNavigation
                .setViewPager(viewPager)

        tabNavigation
                .onTabStripSelectedIndexListener = object :
            NavigationTabStrip.OnTabStripSelectedIndexListener {
            override fun onStartTabSelected(title: String?, index: Int) {
                onTabItemSelected(index, false)
            }

            override fun onEndTabSelected(title: String?, index: Int) {
                onTabItemSelected(index, true)
            }

        }
    }

    /**
     * Called when a tab navigation item was selected
     */
    @CallSuper
    open fun onTabItemSelected(position: Int, wasSelected: Boolean) {

    }

}