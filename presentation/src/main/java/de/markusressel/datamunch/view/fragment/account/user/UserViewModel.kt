package de.markusressel.datamunch.view.fragment.account.user

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.data.persistence.entity.UserEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class UserViewModel : EntityViewModel<UserEntity>() {

    val id = MutableLiveData<Long>()
    // val bsdusr_attributes: Map<String, String>,
    val bsdusr_builtin = MutableLiveData<Boolean>()
    val bsdusr_email = MutableLiveData<String>()
    val bsdusr_full_name = MutableLiveData<String>()
    val bsdusr_group = MutableLiveData<Long>()
    val bsdusr_home = MutableLiveData<String>()
    val bsdusr_locked = MutableLiveData<Boolean>()
    val bsdusr_password_disabled = MutableLiveData<Boolean>()
    val bsdusr_shell = MutableLiveData<String>()
    val bsdusr_smbhash = MutableLiveData<String>()
    val bsdusr_uid = MutableLiveData<Long>()
    val bsdusr_sshpubkey = MutableLiveData<String>()
    val bsdusr_unixhash = MutableLiveData<String>()
    val bsdusr_username = MutableLiveData<String>()
    val bsdusr_sudo = MutableLiveData<Boolean>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<UserEntity>, entityId: Long): Query<UserEntity> {
        return persistenceManager.standardOperation().query {
            equal(UserEntity_.entityId, entityId)
        }
    }

}