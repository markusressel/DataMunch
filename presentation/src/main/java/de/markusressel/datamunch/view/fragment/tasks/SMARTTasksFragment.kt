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

package de.markusressel.datamunch.view.fragment.tasks

import de.markusressel.datamunch.data.persistence.SMARTTaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.smart.SMARTTaskEntity
import de.markusressel.datamunch.data.persistence.entity.smart.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.tasks.smart.SMARTTaskModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class SMARTTasksFragment : ListFragmentBase<SMARTTaskModel, SMARTTaskEntity>() {

    @Inject
    lateinit var persistenceManager: SMARTTaskPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.SMARTTask.id

    override fun getPersistenceHandler(): PersistenceManagerBase<SMARTTaskEntity> = persistenceManager

    override fun loadListDataFromSource(): Single<List<SMARTTaskModel>> {
        return freeNasWebApiClient
                .getSMARTTasks()
    }

    override fun mapToEntity(it: SMARTTaskModel): SMARTTaskEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<SMARTTaskEntity>> {
        return listOf(SortOption.SMART_TASK_TYPE,
                SortOption.SMART_TASK_DESCRIPTION)
    }

    private fun openDetailView(smartTask: SMARTTaskEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(SMARTTaskDetailActivity::class.java, it,
                                    smartTask.entityId)
                    startActivity(intent)
                }
    }

}