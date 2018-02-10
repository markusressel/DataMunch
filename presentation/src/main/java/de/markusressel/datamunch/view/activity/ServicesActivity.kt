package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.ServicesFragment


class ServicesActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_services

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Services
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        val contentFragment = ServicesFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, contentFragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}