package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.storage.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.ScrubsFragment
import de.markusressel.datamunch.view.fragment.storage.TasksFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment
import de.markusressel.datamunch.view.fragment.storage.disk.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.snapshot.SnapshotsFragment


class StoragePage : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() {
            return listOf(R.string.volumes to ::VolumesFragment,
                          R.string.datasets to ::DatasetsFragment,
                          R.string.disks to ::DisksFragment, R.string.scrubs to ::ScrubsFragment,
                          R.string.snapshots to ::SnapshotsFragment,
                          R.string.tasks to ::TasksFragment)
        }

}