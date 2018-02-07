package de.markusressel.datamunch.data.persistence

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Markus on 30.01.2018.
 */
@Singleton
class TemplatePersistenceManager @Inject constructor() : PersistenceManagerBase<TemplateEntity>() {

    override fun getEntityType(): KClass<TemplateEntity> {
        return TemplateEntity::class
    }

}