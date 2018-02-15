package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.storage.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment
import kotlin.reflect.KFunction0


class StorageFragment : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.volumes to ::VolumesFragment,
                          R.string.datasets to ::DatasetsFragment,
                          R.string.disks to ::DisksFragment,
                          R.string.snapshots to ::SnapshotsFragment)
        }

}