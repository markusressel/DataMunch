package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.ServerStatusFragment


class MainActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Status
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_status)

        val serverStatusFragment = ServerStatusFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, serverStatusFragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}