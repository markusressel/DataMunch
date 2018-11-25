package de.markusressel.datamunch.view.fragment.system.alert

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class AlertListViewModel : EntityListViewModel<AlertEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<AlertEntity>): Query<AlertEntity> {
        return persistenceManager.standardOperation().query {}
    }
}