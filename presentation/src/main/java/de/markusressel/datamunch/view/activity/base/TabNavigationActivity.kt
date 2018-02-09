package de.markusressel.datamunch.view.activity.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import kotlin.reflect.KFunction0


/**
 * Created by Markus on 07.02.2018.
 */
abstract class TabNavigationActivity : NavigationDrawerActivity() {

    class TabItemConfig(@StringRes val title: Int, val fragment: KFunction0<Fragment>)

    private val tabNavigation: NavigationTabStrip by lazy {
        findViewById<NavigationTabStrip>(R.id.tabBar)
    }

    private val viewPager: ViewPager by lazy {
        findViewById<ViewPager>(R.id.viewPager)
    }

    abstract fun getTabItems(): List<TabItemConfig>

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        createViewPager()

        setTabBarStyle()
        createTabBar()
    }

    private fun createViewPager() {
        fragmentManager

        viewPager
                .adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                // get fragment and create a new instance
                return getTabItems()[position]
                        .fragment()
            }

            override fun getCount(): Int {
                return getTabItems()
                        .size
            }
        }
        viewPager
                .offscreenPageLimit = getTabItems()
                .size
    }

    private fun setTabBarStyle() {
    }

    private fun createTabBar() {
        // set tab titles
        Single
                .fromObservable(getTabItems().toObservable().map {
                    // convert from StringRes to String
                    getString(it.title)
                }.toList().toObservable())
                .map {
                    it
                            .toTypedArray()
                }
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
    abstract fun onTabItemSelected(position: Int, wasSelected: Boolean)

}

