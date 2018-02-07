package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class UserPersistenceManager @Inject constructor() : PersistenceManagerBase<UserEntity>() {

    override fun getEntityType(): KClass<UserEntity> {
        return UserEntity::class
    }

}