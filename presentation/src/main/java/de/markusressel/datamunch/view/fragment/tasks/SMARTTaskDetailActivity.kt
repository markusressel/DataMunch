package de.markusressel.datamunch.view.fragment.tasks

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SMARTTaskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SMARTTaskEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class SMARTTaskDetailActivity : DetailActivityBase<SMARTTaskEntity>() {

    @Inject
    lateinit var persistenceHandler: SMARTTaskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SMARTTaskEntity> = persistenceHandler

    override val headerTextString: String
        get() = "${getEntity().id}"

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::SMARTTaskDetailContentFragment)


}