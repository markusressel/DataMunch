package de.markusressel.datamunch.view.fragment.tasks

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.smart.SMARTTaskEntity
import de.markusressel.datamunch.data.persistence.entity.smart.SMARTTaskEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class SMARTTaskViewModel : EntityViewModel<SMARTTaskEntity>() {

    val id = MutableLiveData<Long>()
    val smarttest_dayweek = MutableLiveData<String>()
    val smarttest_daymonth = MutableLiveData<String>()
    val smarttest_month = MutableLiveData<String>()
    val smarttest_type = MutableLiveData<String>()
    val smarttest_hour = MutableLiveData<String>()
    val smarttest_desc = MutableLiveData<String>()
    val smarttest_disks = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<SMARTTaskEntity>, entityId: Long): Query<SMARTTaskEntity> {
        return persistenceManager.standardOperation().query {
            equal(SMARTTaskEntity_.entityId, entityId)
        }
    }

}