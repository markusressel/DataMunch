package de.markusressel.datamunch.view.fragment.storage.scrub

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class ScrubListViewModel : EntityListViewModel<ScrubEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<ScrubEntity>): Query<ScrubEntity> {
        return persistenceManager.standardOperation().query {}
    }
}