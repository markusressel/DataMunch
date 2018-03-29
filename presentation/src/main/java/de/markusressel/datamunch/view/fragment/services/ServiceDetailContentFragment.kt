package de.markusressel.datamunch.view.fragment.services

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ServicePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_services_service_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class ServiceDetailContentFragment : DetailContentFragmentBase<ServiceEntity>() {

    @Inject
    protected lateinit var persistenceManager: ServicePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<ServiceEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_services_service_detail


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
                .srv_service

        destinationTextView
                .text = "Enabled: ${entity.srv_enabled}"
    }

}