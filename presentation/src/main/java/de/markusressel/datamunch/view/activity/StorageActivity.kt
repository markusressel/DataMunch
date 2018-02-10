package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.storage.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment


class StorageActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Storage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_storage)
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.volumes, ::VolumesFragment),
                      TabItemConfig(R.string.datasets, ::DatasetsFragment),
                      TabItemConfig(R.string.disks, ::DisksFragment),
                      TabItemConfig(R.string.snapshots, ::SnapshotsFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}