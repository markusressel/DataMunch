package de.markusressel.datamunch.view.fragment.tasks

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.smart.SMARTTaskEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class SMARTTaskListViewModel : EntityListViewModel<SMARTTaskEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<SMARTTaskEntity>): Query<SMARTTaskEntity> {
        return persistenceManager.standardOperation().query {}
    }
}