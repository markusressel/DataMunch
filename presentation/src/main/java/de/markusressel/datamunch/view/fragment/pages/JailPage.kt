package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.jail.jail.JailsFragment
import de.markusressel.datamunch.view.fragment.jail.mountpoint.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.template.TemplatesFragment


class JailPage : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() {
            return listOf(R.string.jails to ::JailsFragment,
                          R.string.mountpoints to ::MountpointsFragment,
                          R.string.templates to ::TemplatesFragment)
        }

}