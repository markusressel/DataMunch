package de.markusressel.datamunch.view.fragment.jail.jail

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class JailListViewModel : EntityListViewModel<JailEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<JailEntity>): Query<JailEntity> {
        return persistenceManager.standardOperation().query {}
    }
}