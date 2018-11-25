package de.markusressel.datamunch.view.fragment.jail.mountpoint

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class MountpointViewModel : EntityViewModel<MountpointEntity>() {

    val id = MutableLiveData<Long>()
    val source = MutableLiveData<String>()
    val destination = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<MountpointEntity>, entityId: Long): Query<MountpointEntity> {
        return persistenceManager.standardOperation().query {
            equal(MountpointEntity_.entityId, entityId)
        }
    }

}