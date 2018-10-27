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

package de.markusressel.datamunch.view.fragment.system.update

import com.airbnb.epoxy.TypedEpoxyController
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.ListItemUpdateBindingModel_
import de.markusressel.datamunch.data.persistence.UpdatePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.system.update.UpdateModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class UpdatesFragment : ListFragmentBase<UpdateModel, UpdateEntity>() {
    @Inject
    lateinit var persistenceManager: UpdatePersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Update.id

    override fun getPersistenceHandler(): PersistenceManagerBase<UpdateEntity> = persistenceManager

    override fun createEpoxyController(): TypedEpoxyController<List<UpdateEntity>> {
        return object : TypedEpoxyController<List<UpdateEntity>>() {
            override fun buildModels(data: List<UpdateEntity>) {
                data.forEach {
                    ListItemUpdateBindingModel_()
                            .id(it.entityId)
                            .item(it)
                            .onclick { model, parentView, clickedView, position ->
                                //                                openDetailView(model.item())
                            }.addTo(this)
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<UpdateModel>> {
        return freeNasWebApiClient
                .getPendingUpdates()
    }

    override fun mapToEntity(it: UpdateModel): UpdateEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<UpdateEntity>> {
        return listOf(SortOption.UPDATE_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Apply Updates",
                                    icon = MaterialDesignIconic.Icon.gmi_check, onClick = {
            applyPendingUpdates()
        }))
    }

    private fun applyPendingUpdates() {
        loadingComponent
                .showLoading()
        freeNasWebApiClient
                .applyPendingUpdates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    loadingComponent
                            .showContent()
                }, onError = {
                    loadingComponent
                            .showError(it)
                })
    }

}