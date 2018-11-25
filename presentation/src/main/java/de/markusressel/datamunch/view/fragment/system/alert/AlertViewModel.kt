package de.markusressel.datamunch.view.fragment.system.alert

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import de.markusressel.datamunch.data.persistence.entity.AlertEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class AlertViewModel : EntityViewModel<AlertEntity>() {

    val id = MutableLiveData<String>()
    val level = MutableLiveData<String>()
    val message = MutableLiveData<String>()
    val dismissed = MutableLiveData<Boolean>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<AlertEntity>, entityId: Long): Query<AlertEntity> {
        return persistenceManager.standardOperation().query {
            equal(AlertEntity_.entityId, entityId)
        }
    }

}