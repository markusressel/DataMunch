package de.markusressel.datamunch.view.fragment.storage.snapshot

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SnapshotPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class SnapshotDetailActivity : DetailActivityBase<SnapshotEntity>() {

    @Inject
    lateinit var persistenceHandler: SnapshotPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SnapshotEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::SnapshotDetailContentFragment)


}