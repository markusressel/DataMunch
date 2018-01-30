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
abstract class PersistenceManagerBase<T : Any> {

    @Inject
    lateinit var boxStore: BoxStore

    private val jailBox: Box<T> by lazy {
        boxStore.boxFor(getEntityType())
    }

    protected abstract fun getEntityType(): KClass<T>

    /**
     * Get an entity by id
     *
     * @param id it's id
     */
    fun get(id: Long): T {
        return jailBox.get(id)
    }

    /**
     * Get all entities of this type
     *
     * @return all entities
     */
    fun getAll(): List<T> {
        return jailBox.all
    }

    /**
     * Persist an entity
     *
     * Hint: Use "0" as the entity ID to add new items.
     *
     * @param entity the entity to persist
     * @return entity id
     */
    fun put(entity: T): Long {
        return jailBox.put(entity)
    }

    /**
     * Remove an entity by id
     */
    fun remove(id: Long) {
        return jailBox.remove(id)
    }

    /**
     * Remove all entities of this type from the persistence
     */
    fun removeAll() {
        return jailBox.removeAll()
    }

}