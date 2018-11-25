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

package de.markusressel.datamunch.view.fragment.sharing.nfs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.nfs.NfsShareEntity
import de.markusressel.datamunch.databinding.ContentSharingNfsDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class NfsShareDetailContentFragment : DetailContentFragmentBase<NfsShareEntity>() {

    @Inject
    protected lateinit var persistenceManager: NfsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<NfsShareEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_sharing_nfs_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentSharingNfsDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(NfsShareViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<NfsShareEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id

            viewModel.nfs_network.value = entity.nfs_network
            viewModel.nfs_hosts.value = entity.nfs_hosts
            viewModel.nfs_comment.value = entity.nfs_comment
            viewModel.nfs_mapall_group.value = entity.nfs_mapall_group
            viewModel.nfs_mapall_user.value = entity.nfs_mapall_user
            viewModel.nfs_maproot_group.value = entity.nfs_maproot_group
            viewModel.nfs_maproot_user.value = entity.nfs_maproot_user
            viewModel.nfs_quiet.value = entity.nfs_quiet
            viewModel.nfs_ro.value = entity.nfs_ro
            // TODO: bind sub entities somehow
//            viewModel.nfs_security.value = entity.nfs_security
//            viewModel.nfs_paths.value = entity.nfs_paths
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}