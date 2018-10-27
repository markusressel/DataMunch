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

package de.markusressel.datamunch.view.fragment.system.alert

import de.markusressel.datamunch.data.persistence.AlertPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.system.alert.AlertModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class AlertsFragment : ListFragmentBase<AlertModel, AlertEntity>() {

    @Inject
    lateinit var persistenceManager: AlertPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Alert.id

    override fun getPersistenceHandler(): PersistenceManagerBase<AlertEntity> = persistenceManager


    override fun loadListDataFromSource(): Single<List<AlertModel>> {
        return freeNasWebApiClient
                .getSystemAlerts()
    }

    override fun mapToEntity(it: AlertModel): AlertEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<AlertEntity>> {
        return listOf(SortOption.ALERT_ENTITY_DISMISSED,
                SortOption.ALERT_ENTITY_LEVEL,
                SortOption.ALERT_ENTITY_MESSAGE
        )
    }

    private fun openDetailView(alert: AlertEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(AlertDetailActivity::class.java, it, alert.entityId)
                    startActivity(intent)
                }
    }

}