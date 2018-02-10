package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.NfsShareEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class NfsSharePersistenceManager @Inject constructor() : PersistenceManagerBase<NfsShareEntity>() {

    override fun getEntityType(): KClass<NfsShareEntity> {
        return NfsShareEntity::class
    }

}