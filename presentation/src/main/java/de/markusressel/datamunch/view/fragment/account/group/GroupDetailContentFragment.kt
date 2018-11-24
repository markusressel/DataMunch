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

package de.markusressel.datamunch.view.fragment.account.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.databinding.ContentAccountsGroupDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class GroupDetailContentFragment : DetailContentFragmentBase<GroupEntity>() {

    @Inject
    protected lateinit var persistenceManager: GroupPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<GroupEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_accounts_group_detail

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val binding: ContentAccountsGroupDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel: GroupViewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<GroupEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id
            viewModel.bsdgrp_builtin.value = entity.bsdgrp_builtin
            viewModel.bsdgrp_gid.value = entity.bsdgrp_gid
            viewModel.bsdgrp_group.value = entity.bsdgrp_group
            viewModel.bsdgrp_sudo.value = entity.bsdgrp_sudo
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding.root
    }
}