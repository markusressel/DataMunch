package de.markusressel.datamunch.data.persistence

import android.support.annotation.CheckResult
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.LastUpdateFromSourceEntity
import de.markusressel.datamunch.data.persistence.entity.LastUpdateFromSourceEntity_
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class LastUpdateFromSourcePersistenceManager @Inject constructor() :
    PersistenceManagerBase<LastUpdateFromSourceEntity>(LastUpdateFromSourceEntity::class) {

    /**
     * Set the "last updated" time for the specified entity type to the current time
     *
     * @param entityModelId the (objectbox-internal) model id of the entity (not a specific entity id but the generated id for the entity type!)
     */
    fun setUpdatedNow(entityModelId: Long) {
        val currentTime = System
                .currentTimeMillis()

        val existingEntity: LastUpdateFromSourceEntity? = getEntity(entityModelId)
        val newEntity = when (existingEntity != null) {
            true -> LastUpdateFromSourceEntity(existingEntity!!.entityId,
                                               existingEntity.entityModelId, currentTime)
            false -> LastUpdateFromSourceEntity(0, entityModelId, currentTime)
        }

        standardOperation()
                .put(newEntity)
    }

    /**
     * Retrieve the time when an entity type was last updated
     *
     * @param entityModelId the (objectbox-internal) model id of the entity (not a specific entity id but the generated id for the entity type!)
     * @return time in milliseconds
     */
    @CheckResult
    fun getLastUpdated(entityModelId: Long): Long {
        val existingEntity: LastUpdateFromSourceEntity? = getEntity(entityModelId)
        return existingEntity?.timeInMilliseconds
                ?: 0
    }

    private fun getEntity(entityModelId: Long): LastUpdateFromSourceEntity? {
        return standardOperation()
                .query()
                .equal(LastUpdateFromSourceEntity_.entityModelId, entityModelId)
                .build()
                .findFirst()
    }


}