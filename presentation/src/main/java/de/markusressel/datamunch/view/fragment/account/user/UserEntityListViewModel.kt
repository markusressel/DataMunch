package de.markusressel.datamunch.view.fragment.account.user

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class UserEntityListViewModel : EntityListViewModel<UserEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<UserEntity>): Query<UserEntity> {
        return persistenceManager.standardOperation().query {}
    }
}