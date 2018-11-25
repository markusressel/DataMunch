package de.markusressel.datamunch.view.fragment.jail.template

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity
import de.markusressel.datamunch.data.persistence.entity.TemplateEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class TemplateViewModel : EntityViewModel<TemplateEntity>() {

    val id = MutableLiveData<Long>()
    val jt_name = MutableLiveData<String>()
    val jt_os = MutableLiveData<String>()
    val jt_instances = MutableLiveData<Long>()
    val jt_arch = MutableLiveData<String>()
    val jt_url = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<TemplateEntity>, entityId: Long): Query<TemplateEntity> {
        return persistenceManager.standardOperation().query {
            equal(TemplateEntity_.entityId, entityId)
        }
    }

}