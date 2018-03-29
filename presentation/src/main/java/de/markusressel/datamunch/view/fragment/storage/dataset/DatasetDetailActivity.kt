package de.markusressel.datamunch.view.fragment.storage.dataset

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DatasetPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DatasetEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class DatasetDetailActivity : DetailActivityBase<DatasetEntity>() {

    @Inject
    lateinit var persistenceHandler: DatasetPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DatasetEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::DatasetDetailContentFragment)


}