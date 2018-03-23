package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.system.MaintenanceFragment
import de.markusressel.datamunch.view.fragment.system.alert.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.update.UpdatesFragment


class SystemPage : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() {
            return listOf(R.string.alerts to ::AlertsFragment,
                          R.string.updates to ::UpdatesFragment,
                          R.string.maintenance to ::MaintenanceFragment)
        }

}