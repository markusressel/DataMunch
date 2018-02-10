package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.account.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.UsersFragment


class AccountsActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Accounts
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.users, ::UsersFragment),
                      TabItemConfig(R.string.groups, ::GroupsFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}