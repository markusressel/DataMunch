package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class JailPersistenceManager @Inject constructor() : PersistenceManagerBase<JailEntity>() {

    override fun getEntityType(): KClass<JailEntity> {
        return JailEntity::class
    }

}