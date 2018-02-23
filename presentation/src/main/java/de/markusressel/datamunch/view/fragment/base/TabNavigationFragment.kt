package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
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