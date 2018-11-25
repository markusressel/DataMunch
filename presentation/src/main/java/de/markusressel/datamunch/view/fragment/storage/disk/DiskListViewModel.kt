package de.markusressel.datamunch.view.fragment.storage.disk

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class DiskListViewModel : EntityListViewModel<DiskEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<DiskEntity>): Query<DiskEntity> {
        return persistenceManager.standardOperation().query {}
    }
}