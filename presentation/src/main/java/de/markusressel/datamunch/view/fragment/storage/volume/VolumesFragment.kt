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

package de.markusressel.datamunch.view.fragment.storage.volume

import android.widget.Toast
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.ListItemLoadingBindingModel_
import de.markusressel.datamunch.ListItemVolumeBindingModel_
import de.markusressel.datamunch.data.persistence.VolumePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity_
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.storage.volume.VolumeModel
import io.objectbox.kotlin.query
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject


/**
 * Created by Markus on 07.01.2018.
 */
class VolumesFragment : ListFragmentBase<VolumeModel, VolumeEntity>() {
    @Inject
    lateinit var persistenceManager: VolumePersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Volume.id

    override fun getPersistenceHandler(): PersistenceManagerBase<VolumeEntity> = persistenceManager

    override fun createEpoxyController(): PagedListEpoxyController<VolumeEntity> {
        return object : PagedListEpoxyController<VolumeEntity>() {
            override fun buildItemModel(currentPosition: Int, item: VolumeEntity?): EpoxyModel<*> {
                return if (item == null) {
                    ListItemLoadingBindingModel_()
                            .id(-currentPosition)
                } else {
                    ListItemVolumeBindingModel_()
                            .id(item.id)
                            .item(item)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<VolumeModel>> {
        return freeNasWebApiClient
                .getVolumes(10000)
    }

    override fun loadListDataFromPersistence(): List<VolumeEntity> {
        val persistenceHandler = getPersistenceHandler()
        return persistenceHandler
                .standardOperation()
                .query {
                    equal(VolumeEntity_.isRoot, true)
                }
                .find()
    }

    override fun mapToEntity(it: VolumeModel): VolumeEntity {
        return it
                .asEntity(true)
    }

    override fun getAllSortCriteria(): List<SortOption<VolumeEntity>> {
        return listOf(SortOption.VOLUME_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                onClick = {
                    openAddDialog()
                }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(volume: VolumeEntity) {
        volume
                .children
                .toObservable()
                .subscribeBy(onNext = {
                    Toast
                            .makeText(context, it.name, Toast.LENGTH_LONG)
                            .show()
                })
    }

}