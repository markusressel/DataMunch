package de.markusressel.datamunch.view.fragment.storage.dataset

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DatasetPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DatasetEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_dataset_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class DatasetDetailContentFragment : DetailContentFragmentBase<DatasetEntity>() {

    @Inject
    protected lateinit var persistenceManager: DatasetPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DatasetEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_dataset_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.entityId}"

        nameTextView
                .text = entity
                .name
    }

}