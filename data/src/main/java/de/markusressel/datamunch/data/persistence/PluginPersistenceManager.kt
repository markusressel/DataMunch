package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class PluginPersistenceManager @Inject constructor() : PersistenceManagerBase<PluginEntity>() {

    override fun getEntityType(): KClass<PluginEntity> {
        return PluginEntity::class
    }

}