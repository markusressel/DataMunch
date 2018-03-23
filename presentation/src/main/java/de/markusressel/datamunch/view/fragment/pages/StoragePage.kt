package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.storage.VolumesFragment
import de.markusressel.datamunch.view.fragment.storage.dataset.DatasetsFragment
import de.markusressel.datamunch.view.fragment.storage.disk.DisksFragment
import de.markusressel.datamunch.view.fragment.storage.scrubs.ScrubsFragment
import de.markusressel.datamunch.view.fragment.storage.snapshot.SnapshotsFragment
import de.markusressel.datamunch.view.fragment.storage.task.TasksFragment


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