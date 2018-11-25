package de.markusressel.datamunch.view.fragment.storage.scrubs

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class ScrubViewModel : EntityViewModel<ScrubEntity>() {

    val id = MutableLiveData<Long>()
    val scrub_threshold = MutableLiveData<Int>()
    val scrub_dayweek = MutableLiveData<String>()
    val scrub_enabled = MutableLiveData<Boolean>()
    val scrub_minute = MutableLiveData<String>()
    val scrub_hour = MutableLiveData<String>()
    val scrub_month = MutableLiveData<String>()
    val scrub_daymonth = MutableLiveData<String>()
    val scrub_description = MutableLiveData<String>()
    val scrub_volume = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<ScrubEntity>, entityId: Long): Query<ScrubEntity> {
        return persistenceManager.standardOperation().query {
            equal(ScrubEntity_.entityId, entityId)
        }
    }

}