package de.markusressel.datamunch.view.fragment.storage.task

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.data.persistence.entity.TaskEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class TaskViewModel : EntityViewModel<TaskEntity>() {

    val id = MutableLiveData<Long>()
    val task_ret_count = MutableLiveData<Long>()
    val task_repeat_unit = MutableLiveData<String>()
    val task_enabled = MutableLiveData<Boolean>()
    val task_recursive = MutableLiveData<Boolean>()
    val task_end = MutableLiveData<String>()
    val task_interval = MutableLiveData<Long>()
    val task_byweekday = MutableLiveData<String>()
    val task_begin = MutableLiveData<String>()
    val task_filesystem = MutableLiveData<String>()
    val task_ret_unit = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<TaskEntity>, entityId: Long): Query<TaskEntity> {
        return persistenceManager.standardOperation().query {
            equal(TaskEntity_.entityId, entityId)
        }
    }

}