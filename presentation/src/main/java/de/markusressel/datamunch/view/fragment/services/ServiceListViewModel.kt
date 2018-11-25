package de.markusressel.datamunch.view.fragment.services

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class ServiceListViewModel : EntityListViewModel<ServiceEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<ServiceEntity>): Query<ServiceEntity> {
        return persistenceManager.standardOperation().query {}
    }
}