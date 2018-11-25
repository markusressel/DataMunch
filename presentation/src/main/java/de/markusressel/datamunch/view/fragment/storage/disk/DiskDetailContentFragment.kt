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

package de.markusressel.datamunch.view.fragment.storage.disk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.databinding.ContentStorageDiskDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class DiskDetailContentFragment : DetailContentFragmentBase<DiskEntity>() {

    @Inject
    protected lateinit var persistenceManager: DiskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_disk_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentStorageDiskDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(DiskViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<DiskEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.entityId

            viewModel.disk_acousticlevel.value = entity.disk_acousticlevel
            viewModel.disk_advpowermgmt.value = entity.disk_advpowermgmt
            viewModel.disk_serial.value = entity.disk_serial
            viewModel.disk_size.value = entity.disk_size
            viewModel.disk_multipath_name.value = entity.disk_multipath_name
            viewModel.disk_identifier.value = entity.disk_identifier
            viewModel.disk_togglesmart.value = entity.disk_togglesmart
            viewModel.disk_hddstandby.value = entity.disk_hddstandby
            viewModel.disk_transfermode.value = entity.disk_transfermode
            viewModel.disk_multipath_member.value = entity.disk_multipath_member
            viewModel.disk_description.value = entity.disk_description
            viewModel.disk_smartoptions.value = entity.disk_smartoptions
            viewModel.disk_expiretime.value = entity.disk_expiretime
            viewModel.disk_name.value = entity.disk_name
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}