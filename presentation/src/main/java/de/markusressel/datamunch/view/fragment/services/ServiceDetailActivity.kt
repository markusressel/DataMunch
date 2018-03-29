package de.markusressel.datamunch.view.fragment.services

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ServicePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class ServiceDetailActivity : DetailActivityBase<ServiceEntity>() {

    @Inject
    lateinit var persistenceHandler: ServicePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<ServiceEntity> = persistenceHandler

    override val headerTextString: String
        get() = "${getEntity().id}"

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::ServiceDetailContentFragment)


}