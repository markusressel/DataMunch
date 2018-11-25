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

package de.markusressel.datamunch.view.fragment.sharing.cifs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.CifsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.databinding.ContentSharingCifsDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class CifsShareDetailContentFragment : DetailContentFragmentBase<CifsShareEntity>() {

    @Inject
    protected lateinit var persistenceManager: CifsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<CifsShareEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_sharing_cifs_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentSharingCifsDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(CifsShareViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<CifsShareEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id
            viewModel.cifs_name.value = entity.cifs_name
            viewModel.cifs_comment.value = entity.cifs_comment
            viewModel.cifs_browsable.value = entity.cifs_browsable
            viewModel.cifs_default_permissions.value = entity.cifs_default_permissions
            viewModel.cifs_guestok.value = entity.cifs_guestok
            viewModel.cifs_home.value = entity.cifs_home
            viewModel.cifs_hostsallow.value = entity.cifs_hostsallow
            viewModel.cifs_hostsdeny.value = entity.cifs_hostsdeny
            viewModel.cifs_auxsmbconf.value = entity.cifs_auxsmbconf
            viewModel.cifs_path.value = entity.cifs_path
            viewModel.cifs_recyclebin.value = entity.cifs_recyclebin
            viewModel.cifs_showhiddenfiles.value = entity.cifs_showhiddenfiles
            viewModel.cifs_ro.value = entity.cifs_ro
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}