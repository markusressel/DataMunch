package de.markusressel.datamunch.view.fragment.jail.template

import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.view.viewmodel.EntityListViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class TemplateListViewModel : EntityListViewModel<TemplateEntity>() {
    override fun createDbQuery(persistenceManager: PersistenceManagerBase<TemplateEntity>): Query<TemplateEntity> {
        return persistenceManager.standardOperation().query {}
    }
}