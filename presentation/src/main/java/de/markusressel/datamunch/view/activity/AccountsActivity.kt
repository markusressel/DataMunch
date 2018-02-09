package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.GroupsFragment
import de.markusressel.datamunch.view.fragment.UsersFragment


class AccountsActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_accounts

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Accounts
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_accounts)
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.users, ::UsersFragment),
                      TabItemConfig(R.string.groups, ::GroupsFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}