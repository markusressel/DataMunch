package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class DiskPersistenceManager @Inject constructor() : PersistenceManagerBase<DiskEntity>() {

    override fun getEntityType(): KClass<DiskEntity> {
        return DiskEntity::class
    }

}