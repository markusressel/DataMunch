package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.system.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.UpdatesFragment
import kotlin.reflect.KFunction0


class SystemActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .System
    }

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.alerts to ::AlertsFragment,
                          R.string.updates to ::UpdatesFragment)
        }

}