package de.markusressel.datamunch.view.fragment.storage.scrubs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ScrubPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class ScrubDetailActivity : DetailActivityBase<ScrubEntity>() {

    @Inject
    lateinit var persistenceHandler: ScrubPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<ScrubEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().scrub_volume

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::ScrubDetailContentFragment)


}