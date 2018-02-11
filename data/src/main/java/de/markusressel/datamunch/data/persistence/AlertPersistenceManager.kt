package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AlertEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class AlertPersistenceManager @Inject constructor() : PersistenceManagerBase<AlertEntity>() {

    override fun getEntityType(): KClass<AlertEntity> {
        return AlertEntity::class
    }

}