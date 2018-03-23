package de.markusressel.datamunch.view.fragment.storage.disk

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class DiskDetailActivity : DetailActivityBase<DiskEntity>() {

    @Inject
    lateinit var persistenceHandler: DiskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().disk_name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::DiskDetailContentFragment)


}