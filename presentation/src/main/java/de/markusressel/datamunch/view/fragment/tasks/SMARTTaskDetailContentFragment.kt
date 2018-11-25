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

package de.markusressel.datamunch.view.fragment.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SMARTTaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.smart.SMARTTaskEntity
import de.markusressel.datamunch.databinding.ContentTasksSmartDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class SMARTTaskDetailContentFragment : DetailContentFragmentBase<SMARTTaskEntity>() {

    @Inject
    protected lateinit var persistenceManager: SMARTTaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SMARTTaskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_tasks_smart_detail


    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentTasksSmartDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(SMARTTaskViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<SMARTTaskEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.entityId

            viewModel.smarttest_dayweek.value = entity.smarttest_dayweek
            viewModel.smarttest_daymonth.value = entity.smarttest_daymonth
            viewModel.smarttest_month.value = entity.smarttest_month
            viewModel.smarttest_type.value = entity.smarttest_type
            viewModel.smarttest_hour.value = entity.smarttest_hour
            viewModel.smarttest_desc.value = entity.smarttest_desc
            viewModel.smarttest_disks.value = entity.smarttest_disks.joinToString(separator = ",") { it.name }
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}