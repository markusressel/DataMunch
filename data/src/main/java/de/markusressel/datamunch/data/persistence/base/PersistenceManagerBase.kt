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