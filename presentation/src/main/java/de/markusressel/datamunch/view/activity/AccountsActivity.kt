package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.account.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.UsersFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import kotlin.reflect.KFunction0


class AccountsActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Accounts
    }

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.users to ::UsersFragment, R.string.groups to ::GroupsFragment)
        }

}