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

package de.markusressel.datamunch.view.fragment.jail.jail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import de.markusressel.datamunch.ListItemJailBindingModel_
import de.markusressel.datamunch.ListItemLoadingBindingModel_
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailFragmentBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.jails.jail.JailModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Markus on 07.01.2018.
 */
class JailsFragment : ListFragmentBase<JailModel, JailEntity>() {
    @Inject
    lateinit var persistenceManager: JailPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Jail.id

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceManager

    override fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ViewDataBinding? {
        val viewModel = ViewModelProviders.of(this).get(JailListViewModel::class.java)
        viewModel.getListLiveData(getPersistenceHandler()).observe(this, Observer {
            epoxyController.submitList(it)
        })

        return super.createViewDataBinding(inflater, container, savedInstanceState)
    }

    override fun createEpoxyController(): PagedListEpoxyController<JailEntity> {
        return object : PagedListEpoxyController<JailEntity>() {
            override fun buildItemModel(currentPosition: Int, item: JailEntity?): EpoxyModel<*> {
                return if (item == null) {
                    ListItemLoadingBindingModel_()
                            .id(-currentPosition)
                } else {
                    ListItemJailBindingModel_()
                            .id(item.id)
                            .item(item)
                            .onclick { model, parentView, clickedView, position ->
                                openDetailView(model.item())
                            }
                }
            }
        }
    }

    override fun loadListDataFromSource(): Single<List<JailModel>> {
        return freeNasWebApiClient
                .getJails(1000)
    }

    override fun mapToEntity(it: JailModel): JailEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<JailEntity>> {
        return listOf(SortOption.JAIL_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                onClick = {
                    openAddDialog()
                }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(jail: JailEntity) {
        navController.navigate(
                R.id.action_jailPage_to_jailDetailPage,
                DetailFragmentBase.createEntityBundle(jail.entityId)
        )
    }

}