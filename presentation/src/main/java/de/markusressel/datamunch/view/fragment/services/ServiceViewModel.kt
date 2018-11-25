package de.markusressel.datamunch.view.fragment.services

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class ServiceViewModel : EntityViewModel<ServiceEntity>() {

    val id = MutableLiveData<Long>()
    val srv_service = MutableLiveData<String>()
    val srv_enabled = MutableLiveData<Boolean>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<ServiceEntity>, entityId: Long): Query<ServiceEntity> {
        return persistenceManager.standardOperation().query {
            equal(ServiceEntity_.entityId, entityId)
        }
    }

}