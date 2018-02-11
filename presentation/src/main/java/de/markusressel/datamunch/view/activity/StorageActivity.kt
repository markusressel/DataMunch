package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.storage.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment
import kotlin.reflect.KFunction0


class StorageActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Storage
    }

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.volumes to ::VolumesFragment,
                          R.string.datasets to ::DatasetsFragment,
                          R.string.disks to ::DisksFragment,
                          R.string.snapshots to ::SnapshotsFragment)
        }

}