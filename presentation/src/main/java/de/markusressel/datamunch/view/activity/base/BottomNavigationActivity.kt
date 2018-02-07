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