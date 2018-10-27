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

package de.markusressel.datamunch.view.fragment.account.group

import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.FabConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.account.group.GroupModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class GroupsFragment : ListFragmentBase<GroupModel, GroupEntity>() {

    @Inject
    lateinit var persistenceManager: GroupPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Group.id

    override fun getPersistenceHandler(): PersistenceManagerBase<GroupEntity> = persistenceManager

    override fun loadListDataFromSource(): Single<List<GroupModel>> {
        return freeNasWebApiClient
                .getGroups()
    }

    override fun mapToEntity(it: GroupModel): GroupEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<GroupEntity>> {
        return listOf(SortOption.GROUP_NAME)
    }

    override fun getRightFabs(): List<FabConfig.Fab> {
        return listOf(FabConfig.Fab(description = "Add", icon = MaterialDesignIconic.Icon.gmi_plus,
                                    onClick = {
                                        openAddDialog()
                                    }))
    }

    private fun openAddDialog() {

    }

    private fun openDetailView(group: GroupEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(GroupDetailActivity::class.java, it, group.entityId)
                    startActivity(intent)
                }
    }

}