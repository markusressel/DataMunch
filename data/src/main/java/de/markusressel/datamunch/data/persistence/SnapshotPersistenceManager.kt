package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class SnapshotPersistenceManager @Inject constructor() : PersistenceManagerBase<SnapshotEntity>() {

    override fun getEntityType(): KClass<SnapshotEntity> {
        return SnapshotEntity::class
    }

}