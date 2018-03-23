package de.markusressel.datamunch.view.fragment.jail.mountpoint

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.MountpointPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class MountpointDetailActivity : DetailActivityBase<MountpointEntity>() {

    @Inject
    lateinit var persistenceHandler: MountpointPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<MountpointEntity> = persistenceHandler

    override val headerTextString: String
        get() = "${getEntity().id}"

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::MountpointDetailContentFragment)


}