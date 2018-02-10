package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UpdateEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class UpdatePersistenceManager @Inject constructor() : PersistenceManagerBase<UpdateEntity>() {

    override fun getEntityType(): KClass<UpdateEntity> {
        return UpdateEntity::class
    }

}