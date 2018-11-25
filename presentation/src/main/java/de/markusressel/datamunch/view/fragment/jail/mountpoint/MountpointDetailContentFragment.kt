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

package de.markusressel.datamunch.view.fragment.jail.mountpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.MountpointPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.databinding.ContentJailsMountpointDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class MountpointDetailContentFragment : DetailContentFragmentBase<MountpointEntity>() {

    @Inject
    protected lateinit var persistenceManager: MountpointPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<MountpointEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jails_mountpoint_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentJailsMountpointDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(MountpointViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<MountpointEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id
            viewModel.source.value = entity.source
            viewModel.destination.value = entity.destination
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}