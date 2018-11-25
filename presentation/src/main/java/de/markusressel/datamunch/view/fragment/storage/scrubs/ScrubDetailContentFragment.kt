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

package de.markusressel.datamunch.view.fragment.storage.scrubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ScrubPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.databinding.ContentStorageScrubDetailBinding
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class ScrubDetailContentFragment : DetailContentFragmentBase<ScrubEntity>() {

    @Inject
    protected lateinit var persistenceManager: ScrubPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<ScrubEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_scrub_detail


    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val binding: ContentStorageScrubDetailBinding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        val viewModel = ViewModelProviders.of(this).get(ScrubViewModel::class.java)
        viewModel.getEntityLiveData(getPersistenceHandler(), entityId).observe(this, Observer<List<ScrubEntity>> {
            val entity = it.first()

            viewModel.id.value = entity.entityId

            viewModel.scrub_threshold.value = entity.scrub_threshold
            viewModel.scrub_dayweek.value = entity.scrub_dayweek
            viewModel.scrub_enabled.value = entity.scrub_enabled
            viewModel.scrub_minute.value = entity.scrub_minute
            viewModel.scrub_hour.value = entity.scrub_hour
            viewModel.scrub_month.value = entity.scrub_month
            viewModel.scrub_daymonth.value = entity.scrub_daymonth
            viewModel.scrub_description.value = entity.scrub_description
            viewModel.scrub_volume.value = entity.scrub_volume
        })

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = viewModel
        }

        return binding
    }

}