package de.markusressel.datamunch.view.fragment.storage.disk

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.data.persistence.entity.DiskEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class DiskViewModel : EntityViewModel<DiskEntity>() {

    val id = MutableLiveData<Long>()
    val disk_acousticlevel = MutableLiveData<String>()
    val disk_advpowermgmt = MutableLiveData<String>()
    val disk_serial = MutableLiveData<String>()
    val disk_size = MutableLiveData<Long>()
    val disk_multipath_name = MutableLiveData<String>()
    val disk_identifier = MutableLiveData<String>()
    val disk_togglesmart = MutableLiveData<Boolean>()
    val disk_hddstandby = MutableLiveData<String>()
    val disk_transfermode = MutableLiveData<String>()
    val disk_multipath_member = MutableLiveData<String>()
    val disk_description = MutableLiveData<String>()
    val disk_smartoptions = MutableLiveData<String>()
    val disk_expiretime = MutableLiveData<Long?>()
    val disk_name = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<DiskEntity>, entityId: Long): Query<DiskEntity> {
        return persistenceManager.standardOperation().query {
            equal(DiskEntity_.entityId, entityId)
        }
    }

}