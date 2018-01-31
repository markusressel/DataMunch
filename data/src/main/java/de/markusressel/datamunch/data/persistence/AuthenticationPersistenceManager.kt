package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AuthenticationEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class AuthenticationPersistenceManager @Inject constructor() : PersistenceManagerBase<AuthenticationEntity>() {

    override fun getEntityType(): KClass<AuthenticationEntity> {
        return AuthenticationEntity::class
    }

}