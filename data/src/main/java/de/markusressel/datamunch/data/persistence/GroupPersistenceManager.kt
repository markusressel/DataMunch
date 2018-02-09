package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class GroupPersistenceManager @Inject constructor() : PersistenceManagerBase<GroupEntity>() {

    override fun getEntityType(): KClass<GroupEntity> {
        return GroupEntity::class
    }

}