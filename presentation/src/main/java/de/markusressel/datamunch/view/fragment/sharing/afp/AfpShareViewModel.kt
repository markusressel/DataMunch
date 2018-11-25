package de.markusressel.datamunch.view.fragment.sharing.afp

import androidx.lifecycle.MutableLiveData
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity_
import de.markusressel.datamunch.view.viewmodel.EntityViewModel
import io.objectbox.kotlin.query
import io.objectbox.query.Query


class AfpShareViewModel : EntityViewModel<AfpShareEntity>() {

    val id = MutableLiveData<Long>()
    val afp_name = MutableLiveData<String>()

    override fun createDbQuery(persistenceManager: PersistenceManagerBase<AfpShareEntity>, entityId: Long): Query<AfpShareEntity> {
        return persistenceManager.standardOperation().query {
            equal(AfpShareEntity_.entityId, entityId)
        }
    }

}