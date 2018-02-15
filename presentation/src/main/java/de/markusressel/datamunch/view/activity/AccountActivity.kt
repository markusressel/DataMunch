package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.pages.AccountFragment

/**
 * Created by Markus on 14.02.2018.
 */
// TODO: setup as fragment instead of activity
class AccountActivity : NavigationDrawerActivity() {
    override val contentFragment: () -> DaggerSupportFragmentBase
        get() = ::AccountFragment

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Accounts
    }

}