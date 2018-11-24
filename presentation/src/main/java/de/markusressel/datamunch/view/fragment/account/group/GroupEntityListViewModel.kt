package de.markusressel.datamunch.view.fragment.account.group

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class GroupEntityListViewModel : EntityListViewModel<GroupEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<GroupEntity>): Query<GroupEntity> {
        return persistenceManager.standardOperation().query {}
    }
}