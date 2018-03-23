package de.markusressel.datamunch.view.fragment.sharing.cifs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.CifsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class CifsShareDetailActivity : DetailActivityBase<CifsShareEntity>() {

    @Inject
    lateinit var persistenceHandler: CifsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<CifsShareEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().cifs_name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::CifsShareDetailContentFragment)


}