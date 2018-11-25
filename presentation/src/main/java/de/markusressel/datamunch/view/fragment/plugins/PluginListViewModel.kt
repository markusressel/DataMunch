package de.markusressel.datamunch.view.fragment.plugins

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class PluginListViewModel : EntityListViewModel<PluginEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<PluginEntity>): Query<PluginEntity> {
        return persistenceManager.standardOperation().query {}
    }
}