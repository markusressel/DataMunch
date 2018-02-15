package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.jail.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.TemplatesFragment
import kotlin.reflect.KFunction0


class PluginFragment : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.jails to ::PluginFragment,
                          R.string.mountpoints to ::MountpointsFragment,
                          R.string.templates to ::TemplatesFragment)
        }

}