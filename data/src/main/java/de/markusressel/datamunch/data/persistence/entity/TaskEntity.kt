/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.datamunch.data.IdentifiableListItem
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.library.storage.task.TaskModel
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
                      val task_filesystem: String, val task_ret_unit: String) : IdentifiableListItem, SearchableListItem {

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(task_filesystem)
    }

}

fun TaskModel.asEntity(entityId: Long = 0): TaskEntity {
    return TaskEntity(entityId, this.id, this.task_ret_count, this.task_repeat_unit,
            this.task_enabled, this.task_recursive, this.task_end, this.task_interval,
            this.task_byweekday, this.task_begin, this.task_filesystem,
            this.task_ret_unit)
}