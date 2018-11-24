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

package de.markusressel.datamunch.view.fragment.storage.snapshot

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.ListItemLoadingBindingModel_
import de.markusressel.datamunch.ListItemSnapshotBindingModel_
import de.markusressel.datamunch.data.persistence.SnapshotPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.snapshot.SnapshotModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Markus on 07.01.2018.
 */
class SnapshotsFragment : ListFragmentBase<SnapshotModel, SnapshotEntity>() {
    @Inject
    lateinit var persistenceManager: SnapshotPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Snapshot.id

    override fun getPersistenceHandler(): PersistenceManagerBase<SnapshotEntity> = persistenceManager

    override fun createEpoxyController(): PagedListEpoxyController<SnapshotEntity> {
        return object : PagedListEpoxyController<SnapshotEntity>() {
            override fun buildItemModel(currentPosition: Int, item: SnapshotEntity?): EpoxyModel<*> {
                return if (item == null) {
                    ListItemLoadingBindingModel_()
                            .id(-currentPosition)
                } else {
                    ListItemSnapshotBindingModel_()
                            .id(item.id)
                            .item(item)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<SnapshotModel>> {
        return freeNasWebApiClient
                .getSnapshots(limit = 10000)
    }

    override fun mapToEntity(it: SnapshotModel): SnapshotEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<SnapshotEntity>> {
        return listOf(SortOption.SNAPSHOT_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                onClick = {
                    openAddDialog()
                }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(volume: SnapshotEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(SnapshotDetailActivity::class.java, it,
                                    volume.entityId)
                    startActivity(intent)
                }
    }

}