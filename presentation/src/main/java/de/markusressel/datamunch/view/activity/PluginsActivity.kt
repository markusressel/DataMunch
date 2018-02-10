package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.PluginsFragment


class PluginsActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_plugins

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Plugins
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        val contentFragment = PluginsFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, contentFragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}