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