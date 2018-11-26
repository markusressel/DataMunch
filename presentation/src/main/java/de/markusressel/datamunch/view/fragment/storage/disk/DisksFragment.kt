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
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import de.markusressel.datamunch.ListItemDiskBindingModel_
import de.markusressel.datamunch.ListItemLoadingBindingModel_
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailFragmentBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.disk.DiskModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Markus on 07.01.2018.
 */
class DisksFragment : ListFragmentBase<DiskModel, DiskEntity>() {
    @Inject
    lateinit var persistenceManager: DiskPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Disk.id

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceManager

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val viewModel = ViewModelProviders.of(this).get(DiskListViewModel::class.java)
        viewModel.getListLiveData(getPersistenceHandler()).observe(this, Observer {
            epoxyController.submitList(it)
        })

        return super.createViewDataBinding(inflater, container, savedInstanceState)
    }

    override fun createEpoxyController(): PagedListEpoxyController<DiskEntity> {
        return object : PagedListEpoxyController<DiskEntity>() {
            override fun buildItemModel(currentPosition: Int, item: DiskEntity?): EpoxyModel<*> {
                return if (item == null) {
                    ListItemLoadingBindingModel_()
                            .id(-currentPosition)
                } else {
                    ListItemDiskBindingModel_().id(item.entityId)
                            .item(item)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<DiskModel>> {
        return freeNasWebApiClient
                .getDisks(1000)
    }

    override fun mapToEntity(it: DiskModel): DiskEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<DiskEntity>> {
        return listOf(SortOption.DISK_ENTITY_NAME)
    }

    private fun openDetailView(disk: DiskEntity) {
        navController.navigate(
                R.id.action_storagePage_to_diskDetailPage,
                DetailFragmentBase.createEntityBundle(disk.entityId)
        )
    }

}