package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.VolumeEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class VolumePersistenceManager @Inject constructor() : PersistenceManagerBase<VolumeEntity>() {

    override fun getEntityType(): KClass<VolumeEntity> {
        return VolumeEntity::class
    }

}