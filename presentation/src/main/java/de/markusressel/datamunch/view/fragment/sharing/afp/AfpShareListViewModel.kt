package de.markusressel.datamunch.view.fragment.sharing.afp

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class AfpShareListViewModel : EntityListViewModel<AfpShareEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<AfpShareEntity>): Query<AfpShareEntity> {
        return persistenceManager.standardOperation().query {}
    }
}