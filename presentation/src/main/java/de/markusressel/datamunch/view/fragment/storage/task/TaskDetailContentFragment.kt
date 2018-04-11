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

package de.markusressel.datamunch.view.fragment.storage.task

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_task_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class TaskDetailContentFragment : DetailContentFragmentBase<TaskEntity>() {

    @Inject
    protected lateinit var persistenceManager: TaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TaskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_task_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        nameTextView
                .text = entity
                .task_ret_unit
        enabledCheckBox
                .isChecked = entity
                .task_enabled
        recursiveCheckBox
                .isChecked = entity
                .task_recursive
        intervalTextView
                .text = "${entity.task_interval}"
        retCountTextView
                .text = "${entity.task_ret_count}"
        repeatUnitTextView
                .text = entity
                .task_repeat_unit
        beginTextView
                .text = entity
                .task_begin
        endTextView
                .text = entity
                .task_end
        byWeekDayTextView
                .text = entity
                .task_byweekday
        filesystemTextView
                .text = entity
                .task_filesystem

    }

}