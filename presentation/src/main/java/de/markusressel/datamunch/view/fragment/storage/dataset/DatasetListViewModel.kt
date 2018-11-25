package de.markusressel.datamunch.view.fragment.storage.dataset

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.dataset.DatasetEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class DatasetListViewModel : EntityListViewModel<DatasetEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<DatasetEntity>): Query<DatasetEntity> {
        return persistenceManager.standardOperation().query {}
    }
}