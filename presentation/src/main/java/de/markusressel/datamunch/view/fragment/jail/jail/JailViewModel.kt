package de.markusressel.datamunch.view.fragment.jail.jail

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.persistence.entity.JailEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class JailViewModel : EntityViewModel<JailEntity>() {

    val id = MutableLiveData<Long>()
    val jail_host = MutableLiveData<String>()
    val jail_status = MutableLiveData<String>()
    val jail_ipv4 = MutableLiveData<String>()
    val jail_mac = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<JailEntity>, entityId: Long): Query<JailEntity> {
        return persistenceManager.standardOperation().query {
            equal(JailEntity_.entityId, entityId)
        }
    }

}