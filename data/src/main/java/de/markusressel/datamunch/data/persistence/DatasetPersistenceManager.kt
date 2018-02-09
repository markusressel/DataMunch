package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DatasetEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class DatasetPersistenceManager @Inject constructor() : PersistenceManagerBase<DatasetEntity>() {

    override fun getEntityType(): KClass<DatasetEntity> {
        return DatasetEntity::class
    }

}