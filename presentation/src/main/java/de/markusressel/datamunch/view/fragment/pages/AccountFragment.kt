package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.account.GroupsFragment
import de.markusressel.datamunch.view.fragment.account.UsersFragment
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import kotlin.reflect.KFunction0


class AccountFragment : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.users to ::UsersFragment, R.string.groups to ::GroupsFragment)
        }

}