/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.data.persistence.base

import de.markusressel.datamunch.data.EntityWithId
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Base class of a persistance manager for a specific type
 *
 * Created by Markus on 30.01.2018.
 */
abstract class PersistenceManagerBase<EntityType : EntityWithId>(val entityType: KClass<EntityType>) {

    @Inject
    protected lateinit var boxStore: BoxStore

    private val box: Box<EntityType> by lazy {
        boxStore.boxFor(entityType)
    }

    /**
     * Get the model id for this entity type (same for all entities of equal class type without dependency on the class name)
     */
    fun getEntityModelId(): Int {
        val entityClass: Class<EntityType> = entityType.java
        return boxStore.getEntityTypeIdOrThrow(entityClass)
    }

    /**
     * Returns the BoxStore for this PersistenceManager to perform standard (ObjectBox) operations
     */
    fun standardOperation(): Box<EntityType> {
        return box
    }

    /**
     * Removes database files entirely
     */
    fun clearData() {
        boxStore.close()
        boxStore.deleteAllFiles()
    }

    /**
     * Inserts missing, updates existing and deletes entities from persistence not present in the given dataset.
     */
    fun insertUpdateAndCleanup(data: List<EntityType>) {
        val existingItemIdsToEntities = standardOperation().all.map { it.getItemId() to it }.toMap()
        data.forEach {
            if (it.getItemId() in existingItemIdsToEntities.keys) {
                // there is already an existing entity for this which we will overwrite (by using the same entityId)
                val existingEntity = existingItemIdsToEntities.getValue(it.getItemId())
                it.setDbEntityId(existingEntity.getDbEntityId())
                standardOperation().put(it)
            } else {
                // this is a new entity, just insert it
                standardOperation().put(it)
            }
        }

        deleteMissing(data)
    }

    private fun deleteMissing(data: List<EntityType>) {
        val entityIdsToPreserve = data.map { it.getItemId() }
        val allEntities = standardOperation().all
        val partitionedIds = allEntities.partition {
            it.getItemId() in entityIdsToPreserve
        }

        standardOperation().removeByKeys(partitionedIds.second.map { it.getDbEntityId() })
    }


}