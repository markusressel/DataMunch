package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.JailsFragment


class JailsActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_jails

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder.Jails.identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.menu_item_jails)

        val contentFragment = JailsFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentLayout, contentFragment)
//                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}