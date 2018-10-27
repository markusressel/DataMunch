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

import com.airbnb.epoxy.TypedEpoxyController
import de.markusressel.datamunch.ListItemDiskBindingModel_
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.disk.DiskModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class DisksFragment : ListFragmentBase<DiskModel, DiskEntity>() {
    @Inject
    lateinit var persistenceManager: DiskPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Disk.id

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceManager

    override fun createEpoxyController(): TypedEpoxyController<List<DiskEntity>> {
        return object : TypedEpoxyController<List<DiskEntity>>() {
            override fun buildModels(data: List<DiskEntity>) {
                data.forEach {
                    ListItemDiskBindingModel_()
                            .id(it.entityId)
                            .item(it)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }.addTo(this)
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<DiskModel>> {
        return freeNasWebApiClient
                .getDisks()
    }

    override fun mapToEntity(it: DiskModel): DiskEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<DiskEntity>> {
        return listOf(SortOption.DISK_ENTITY_NAME)
    }

    private fun openDetailView(disk: DiskEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(DiskDetailActivity::class.java, it, disk.entityId)
                    startActivity(intent)
                }
    }

}