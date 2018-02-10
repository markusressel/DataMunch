package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class CifsSharePersistenceManager @Inject constructor() :
    PersistenceManagerBase<CifsShareEntity>() {

    override fun getEntityType(): KClass<CifsShareEntity> {
        return CifsShareEntity::class
    }

}