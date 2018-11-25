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

package de.markusressel.datamunch.view.fragment.jail.template

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.TemplatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.databinding.ContentJailsTemplateDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class TemplateDetailContentFragment : DetailContentFragmentBase<TemplateEntity>() {

    @Inject
    protected lateinit var persistenceManager: TemplatePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<TemplateEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jails_template_detail

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentJailsTemplateDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(TemplateViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<TemplateEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.id
            viewModel.jt_name.value = entity.jt_name
            viewModel.jt_os.value = entity.jt_os
            viewModel.jt_instances.value = entity.jt_instances
            viewModel.jt_arch.value = entity.jt_arch
            viewModel.jt_url.value = entity.jt_url
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}