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

package de.markusressel.datamunch.view.fragment.services

import de.markusressel.datamunch.data.persistence.ServicePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.services.service.ServiceModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServicesFragment : ListFragmentBase<ServiceModel, ServiceEntity>() {

    @Inject
    lateinit var persistenceManager: ServicePersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Service.id

    override fun getPersistenceHandler(): PersistenceManagerBase<ServiceEntity> = persistenceManager


    override fun loadListDataFromSource(): Single<List<ServiceModel>> {
        return freeNasWebApiClient
                .getServices()
    }

    override fun mapToEntity(it: ServiceModel): ServiceEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<ServiceEntity>> {
        return listOf(SortOption.SERVICE_ENTITY_NAME)
    }

    private fun openDetailView(service: ServiceEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(ServiceDetailActivity::class.java, it,
                                               service.entityId)
                    startActivity(intent)
                }
    }

}