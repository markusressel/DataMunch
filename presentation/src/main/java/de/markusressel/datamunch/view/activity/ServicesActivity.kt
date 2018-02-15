package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.ServicesFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase

// TODO: setup as fragment instead of activity
class ServicesActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Services
    }

    override val contentFragment: () -> DaggerSupportFragmentBase
        get() = ::ServicesFragment

}