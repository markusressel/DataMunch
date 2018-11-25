package de.markusressel.datamunch.view.fragment.plugins

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.data.persistence.entity.PluginEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class PluginViewModel : EntityViewModel<PluginEntity>() {

    val id = MutableLiveData<Long>()
    val plugin_name = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<PluginEntity>, entityId: Long): Query<PluginEntity> {
        return persistenceManager.standardOperation().query {
            equal(PluginEntity_.entityId, entityId)
        }
    }

}