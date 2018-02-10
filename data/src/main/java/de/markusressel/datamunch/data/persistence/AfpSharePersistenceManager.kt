package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class AfpSharePersistenceManager @Inject constructor() : PersistenceManagerBase<AfpShareEntity>() {

    override fun getEntityType(): KClass<AfpShareEntity> {
        return AfpShareEntity::class
    }

}