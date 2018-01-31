package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.HostEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class HostPersistenceManager @Inject constructor() : PersistenceManagerBase<HostEntity>() {

    override fun getEntityType(): KClass<HostEntity> {
        return HostEntity::class
    }

    /**
     * Get the currently active host
     */
    fun getActive(): HostEntity? {
        return standardOperation()
                .query()
                .filter { it.isActive }
                .build()
                .findFirst()
    }

}