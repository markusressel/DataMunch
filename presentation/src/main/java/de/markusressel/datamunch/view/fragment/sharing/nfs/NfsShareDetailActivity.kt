package de.markusressel.datamunch.view.fragment.sharing.nfs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.NfsShareEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class NfsShareDetailActivity : DetailActivityBase<NfsShareEntity>() {

    @Inject
    lateinit var persistenceHandler: NfsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<NfsShareEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().nfs_network

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::NfsShareDetailContentFragment)


}