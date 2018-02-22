package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.storage.task.TaskModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class TaskEntity(@Id var entityId: Long, val id: Long, val task_ret_count: Long,
                      val task_repeat_unit: String, val task_enabled: Boolean,
                      val task_recursive: Boolean, val task_end: String, val task_interval: Long,
                      val task_byweekday: String, val task_begin: String,
                      val task_filesystem: String, val task_ret_unit: String)

fun TaskModel.asEntity(entityId: Long = 0): TaskEntity {
    return TaskEntity(entityId, this.id, this.task_ret_count, this.task_repeat_unit,
                      this.task_enabled, this.task_recursive, this.task_end, this.task_interval,
                      this.task_byweekday, this.task_begin, this.task_filesystem,
                      this.task_ret_unit)
}