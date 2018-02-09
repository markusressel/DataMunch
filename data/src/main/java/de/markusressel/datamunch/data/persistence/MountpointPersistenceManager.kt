package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class MountpointPersistenceManager @Inject constructor() :
    PersistenceManagerBase<MountpointEntity>() {

    override fun getEntityType(): KClass<MountpointEntity> {
        return MountpointEntity::class
    }

}