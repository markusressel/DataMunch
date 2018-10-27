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

package de.markusressel.datamunch.view.fragment.sharing.afp

import com.airbnb.epoxy.TypedEpoxyController
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.ListItemAfpShareBindingModel_
import de.markusressel.datamunch.data.persistence.AfpSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.sharing.afp.AfpShareModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AfpSharesFragment : ListFragmentBase<AfpShareModel, AfpShareEntity>() {
    @Inject
    lateinit var persistenceManager: AfpSharePersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.AfpShare.id

    override fun getPersistenceHandler(): PersistenceManagerBase<AfpShareEntity> = persistenceManager

    override fun createEpoxyController(): TypedEpoxyController<List<AfpShareEntity>> {
        return object : TypedEpoxyController<List<AfpShareEntity>>() {
            override fun buildModels(data: List<AfpShareEntity>) {
                data.forEach {
                    ListItemAfpShareBindingModel_()
                            .id(it.id)
                            .item(it)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }.addTo(this)
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<AfpShareModel>> {
        return freeNasWebApiClient
                .getAfpShares()
    }

    override fun mapToEntity(it: AfpShareModel): AfpShareEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<AfpShareEntity>> {
        return listOf(SortOption.AFP_SHARE_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
                                        openAddView()
                                    }))
    }

    private fun openAddView() {
    }

    private fun openDetailView(share: AfpShareEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(AfpShareDetailActivity::class.java, it,
                                               share.entityId)
                    startActivity(intent)
                }
    }

}