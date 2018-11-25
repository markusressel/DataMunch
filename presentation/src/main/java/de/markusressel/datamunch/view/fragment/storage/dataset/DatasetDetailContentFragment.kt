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

package de.markusressel.datamunch.view.fragment.storage.dataset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DatasetPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.dataset.DatasetEntity
import de.markusressel.datamunch.databinding.ContentStorageDatasetDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class DatasetDetailContentFragment : DetailContentFragmentBase<DatasetEntity>() {

    @Inject
    protected lateinit var persistenceManager: DatasetPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DatasetEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_dataset_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentStorageDatasetDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(DatasetViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<DatasetEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.entityId

            viewModel.atime.value = entity.atime
            viewModel.avail.value = entity.avail
            viewModel.comments.value = entity.comments
            viewModel.compression.value = entity.compression
            viewModel.dedup.value = entity.dedup
            viewModel.mountpoint.value = entity.mountpoint
            viewModel.name.value = entity.name
            viewModel.pool.value = entity.pool
            viewModel.quota.value = entity.quota
            viewModel.readonly.value = entity.readonly == "on"
            viewModel.recordsize.value = entity.recordsize
            viewModel.refer.value = entity.refer
            viewModel.refquota.value = entity.refquota
            viewModel.refreservation.value = entity.refreservation
            viewModel.reservation.value = entity.reservation
            viewModel.used.value = entity.used
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}