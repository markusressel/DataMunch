package de.markusressel.datamunch.view.fragment.storage.dataset

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.dataset.DatasetEntity
import de.markusressel.datamunch.data.persistence.entity.dataset.DatasetEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class DatasetViewModel : EntityViewModel<DatasetEntity>() {

    val id = MutableLiveData<Long>()
    val atime = MutableLiveData<String>()
    val avail = MutableLiveData<Long>()
    val comments = MutableLiveData<String?>()
    val compression = MutableLiveData<String>()
    val dedup = MutableLiveData<String>()
    val mountpoint = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val pool = MutableLiveData<String>()
    val quota = MutableLiveData<Long>()
    val readonly = MutableLiveData<Boolean>()
    val recordsize = MutableLiveData<Long>()
    val refer = MutableLiveData<Long>()
    val refquota = MutableLiveData<Long>()
    val refreservation = MutableLiveData<Long>()
    val reservation = MutableLiveData<Long>()
    val used = MutableLiveData<Long>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<DatasetEntity>, entityId: Long): Query<DatasetEntity> {
        return persistenceManager.standardOperation().query {
            equal(DatasetEntity_.entityId, entityId)
        }
    }

}