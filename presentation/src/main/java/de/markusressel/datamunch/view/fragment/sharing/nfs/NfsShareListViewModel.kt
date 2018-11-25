package de.markusressel.datamunch.view.fragment.sharing.nfs

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.nfs.NfsShareEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class NfsShareListViewModel : EntityListViewModel<NfsShareEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<NfsShareEntity>): Query<NfsShareEntity> {
        return persistenceManager.standardOperation().query {}
    }
}