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
open class PersistenceManagerBase<EntityType : Any>(val entityType: KClass<EntityType>) {

    @Inject
    protected lateinit var boxStore: BoxStore

    private val box: Box<EntityType> by lazy {
        boxStore
                .boxFor(entityType)
    }

    /**
     * Get the model id for this entity type (same for all entities of equal class type without dependency on the class name)
     */
    fun getEntityModelId(): Int {
        val entityClass: Class<EntityType> = entityType
                .java

        return boxStore
                .getEntityTypeIdOrThrow(entityClass)
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
        boxStore
                .close()
        boxStore
                .deleteAllFiles()
    }

}