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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TaskEntity
import de.markusressel.datamunch.databinding.ContentStorageTaskDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
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

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentStorageTaskDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<TaskEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.entityId

            viewModel.task_ret_count.value = entity.task_ret_count
            viewModel.task_ret_unit.value = entity.task_ret_unit
            viewModel.task_repeat_unit.value = entity.task_repeat_unit
            viewModel.task_enabled.value = entity.task_enabled
            viewModel.task_recursive.value = entity.task_recursive
            viewModel.task_interval.value = entity.task_interval
            viewModel.task_byweekday.value = entity.task_byweekday
            viewModel.task_begin.value = entity.task_begin
            viewModel.task_end.value = entity.task_end
            viewModel.task_filesystem.value = entity.task_filesystem
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}