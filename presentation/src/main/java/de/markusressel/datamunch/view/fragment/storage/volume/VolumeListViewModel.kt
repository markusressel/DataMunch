package de.markusressel.datamunch.view.fragment.storage.volume

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class VolumeListViewModel : EntityListViewModel<VolumeEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<VolumeEntity>): Query<VolumeEntity> {
        return persistenceManager.standardOperation().query {}
    }
}