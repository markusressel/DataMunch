package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.jail.JailsFragment
import de.markusressel.datamunch.view.fragment.jail.MountpointsFragment
import de.markusressel.datamunch.view.fragment.jail.TemplatesFragment
import kotlin.reflect.KFunction0


class JailsActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Jails
    }

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.jails to ::JailsFragment,
                          R.string.mountpoints to ::MountpointsFragment,
                          R.string.templates to ::TemplatesFragment)
        }

}