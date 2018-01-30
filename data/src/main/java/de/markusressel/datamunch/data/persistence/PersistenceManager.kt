package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.entity.JailEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class PersistenceManager @Inject constructor() {

    @Inject
    lateinit var boxStore: BoxStore

    private val jailBox: Box<JailEntity> by lazy {
        boxStore.boxFor(JailEntity::class.java)
    }

    /**
     * Persist a jail
     *
     * @param jail the jail newEntity
     * @return newEntity id
     */
    fun put(jail: JailEntity): Long {
        return jailBox.put(jail)
    }

    /**
     * Get a jail
     *
     * @param id it's id
     * @return all jail entities
     */
    fun getAllJails(): List<JailEntity> {
        return jailBox.all
    }

    /**
     * Get a jail
     *
     * @param id it's id
     */
    fun get(id: Long): JailEntity {
        return jailBox.get(id)
    }

    /**
     * Remove all jails from the persistence
     */
    fun removeAllJails() {
        return jailBox.removeAll()
    }

}