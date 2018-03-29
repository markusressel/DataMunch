package de.markusressel.datamunch.view.fragment.tasks

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SMARTTaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SMARTTaskEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_services_service_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class SMARTTaskDetailContentFragment : DetailContentFragmentBase<SMARTTaskEntity>() {

    @Inject
    protected lateinit var persistenceManager: SMARTTaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SMARTTaskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_tasks_smart_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.id}"

        sourceTextView
                .text = entity
                .smarttest_desc

        destinationTextView
                .text = "Enabled: ${entity.smarttest_type}"
    }

}