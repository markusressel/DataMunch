package de.markusressel.datamunch.view.fragment.storage.snapshot

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class SnapshotListViewModel : EntityListViewModel<SnapshotEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<SnapshotEntity>): Query<SnapshotEntity> {
        return persistenceManager.standardOperation().query {}
    }
}