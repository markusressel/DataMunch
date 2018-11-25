package de.markusressel.datamunch.view.fragment.storage.snapshot

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class SnapshotViewModel : EntityViewModel<SnapshotEntity>() {

    val id = MutableLiveData<String>()
    val filesystem = MutableLiveData<String>()
    val fullname = MutableLiveData<String>()
    val mostrecent = MutableLiveData<Boolean>()
    val name = MutableLiveData<String>()
    val parent_type = MutableLiveData<String>()
    val refer = MutableLiveData<String>()
    val used = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<SnapshotEntity>, entityId: Long): Query<SnapshotEntity> {
        return persistenceManager.standardOperation().query {
            equal(SnapshotEntity_.entityId, entityId)
        }
    }

}