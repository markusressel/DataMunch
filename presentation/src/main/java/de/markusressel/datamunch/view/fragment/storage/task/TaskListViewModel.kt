package de.markusressel.datamunch.view.fragment.storage.task

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class TaskListViewModel : EntityListViewModel<TaskEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<TaskEntity>): Query<TaskEntity> {
        return persistenceManager.standardOperation().query {}
    }
}