package de.markusressel.datamunch.view.fragment.jail.mountpoint

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class MountpointListViewModel : EntityListViewModel<MountpointEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<MountpointEntity>): Query<MountpointEntity> {
        return persistenceManager.standardOperation().query {}
    }
}