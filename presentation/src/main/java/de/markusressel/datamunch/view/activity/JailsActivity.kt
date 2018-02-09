package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.JailsFragment
import de.markusressel.datamunch.view.fragment.MountpointsFragment
import de.markusressel.datamunch.view.fragment.TemplatesFragment


class JailsActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_jails

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Jails
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_jails)
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.jails, ::JailsFragment),
                      TabItemConfig(R.string.mountpoints, ::MountpointsFragment),
                      TabItemConfig(R.string.templates, ::TemplatesFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}