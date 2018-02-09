package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.DisksFragment
import de.markusressel.datamunch.view.fragment.VolumesFragment


class StorageActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Storage
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_storage)
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.volumes, ::VolumesFragment),
                      TabItemConfig(R.string.disks, ::DisksFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}