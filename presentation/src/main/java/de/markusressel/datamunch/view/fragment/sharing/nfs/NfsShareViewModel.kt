package de.markusressel.datamunch.view.fragment.sharing.nfs

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.nfs.NfsShareEntity
import de.markusressel.datamunch.data.persistence.entity.nfs.NfsShareEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class NfsShareViewModel : EntityViewModel<NfsShareEntity>() {

    val id = MutableLiveData<Long>()
    val nfs_network = MutableLiveData<String>()
    val nfs_hosts = MutableLiveData<String>()
    val nfs_comment = MutableLiveData<String>()
    val nfs_mapall_group = MutableLiveData<String>()
    val nfs_mapall_user = MutableLiveData<String>()
    val nfs_maproot_group = MutableLiveData<String>()
    val nfs_maproot_user = MutableLiveData<String>()
    val nfs_quiet = MutableLiveData<Boolean>()
    val nfs_ro = MutableLiveData<Boolean>()
    val nfs_security = MutableLiveData<List<String>>()
    val nfs_paths = MutableLiveData<List<String>>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<NfsShareEntity>, entityId: Long): Query<NfsShareEntity> {
        return persistenceManager.standardOperation().query {
            equal(NfsShareEntity_.entityId, entityId)
        }
    }

}