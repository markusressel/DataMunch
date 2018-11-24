package de.markusressel.datamunch.view.fragment.account.group

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.data.persistence.entity.GroupEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class GroupViewModel : EntityViewModel<GroupEntity>() {

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<GroupEntity>, entityId: Long): Query<GroupEntity> {
        return persistenceManager.standardOperation().query {
            equal(GroupEntity_.entityId, entityId)
        }
    }

    val id = MutableLiveData<Long>()
    val bsdgrp_builtin = MutableLiveData<Boolean>()
    val bsdgrp_gid = MutableLiveData<Long>()
    val bsdgrp_group = MutableLiveData<String>()
    val bsdgrp_sudo = MutableLiveData<Boolean>()

}