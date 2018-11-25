package de.markusressel.datamunch.view.fragment.sharing.cifs

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class CifsShareViewModel : EntityViewModel<CifsShareEntity>() {

    val id = MutableLiveData<Long>()
    val cifs_name = MutableLiveData<String>()
    val cifs_comment = MutableLiveData<String>()
    val cifs_browsable = MutableLiveData<Boolean>()
    val cifs_default_permissions = MutableLiveData<Boolean>()
    val cifs_guestok = MutableLiveData<Boolean>()
    val cifs_home = MutableLiveData<Boolean>()
    val cifs_hostsallow = MutableLiveData<String>()
    val cifs_hostsdeny = MutableLiveData<String>()
    val cifs_auxsmbconf = MutableLiveData<String>()
    val cifs_path = MutableLiveData<String>()
    val cifs_recyclebin = MutableLiveData<Boolean>()
    val cifs_showhiddenfiles = MutableLiveData<Boolean>()
    val cifs_ro = MutableLiveData<Boolean>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<CifsShareEntity>, entityId: Long): Query<CifsShareEntity> {
        return persistenceManager.standardOperation().query {
            equal(CifsShareEntity_.entityId, entityId)
        }
    }

}