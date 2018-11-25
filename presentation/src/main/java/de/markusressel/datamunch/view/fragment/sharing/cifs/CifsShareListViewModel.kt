package de.markusressel.datamunch.view.fragment.sharing.cifs

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class CifsShareListViewModel : EntityListViewModel<CifsShareEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<CifsShareEntity>): Query<CifsShareEntity> {
        return persistenceManager.standardOperation().query {}
    }
}