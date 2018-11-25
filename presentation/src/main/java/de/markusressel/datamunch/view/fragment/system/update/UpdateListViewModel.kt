package de.markusressel.datamunch.view.fragment.system.update

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class UpdateListViewModel : EntityListViewModel<UpdateEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<UpdateEntity>): Query<UpdateEntity> {
        return persistenceManager.standardOperation().query {}
    }
}