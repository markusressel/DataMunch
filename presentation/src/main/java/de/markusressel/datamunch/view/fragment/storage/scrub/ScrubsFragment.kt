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

package de.markusressel.datamunch.view.fragment.storage.scrub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import de.markusressel.datamunch.ListItemLoadingBindingModel_
import de.markusressel.datamunch.ListItemScrubBindingModel_
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ScrubPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailFragmentBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.scrub.ScrubModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Markus on 07.01.2018.
 */
class ScrubsFragment : ListFragmentBase<ScrubModel, ScrubEntity>() {
    @Inject
    lateinit var persistenceManager: ScrubPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Scrub.id

    override fun getPersistenceHandler(): PersistenceManagerBase<ScrubEntity> = persistenceManager

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val viewModel = ViewModelProviders.of(this).get(ScrubListViewModel::class.java)
        viewModel.getListLiveData(getPersistenceHandler()).observe(this, Observer {
            epoxyController.submitList(it)
        })

        return super.createViewDataBinding(inflater, container, savedInstanceState)
    }

    override fun createEpoxyController(): PagedListEpoxyController<ScrubEntity> {
        return object : PagedListEpoxyController<ScrubEntity>() {
            override fun buildItemModel(currentPosition: Int, item: ScrubEntity?): EpoxyModel<*> {
                return if (item == null) {
                    ListItemLoadingBindingModel_()
                            .id(-currentPosition)
                } else {
                    ListItemScrubBindingModel_()
                            .id(item.id)
                            .item(item)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<ScrubModel>> {
        return freeNasWebApiClient
                .getScrubs(10000)
    }

    override fun mapToEntity(it: ScrubModel): ScrubEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<ScrubEntity>> {
        return listOf(SortOption.SCRUB_VOLUME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                onClick = {
                    openAddDialog()
                }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(scrub: ScrubEntity) {
        navController.navigate(
                R.id.action_storagePage_to_scrubDetailPage,
                DetailFragmentBase.createEntityBundle(scrub.entityId)
        )
    }

}