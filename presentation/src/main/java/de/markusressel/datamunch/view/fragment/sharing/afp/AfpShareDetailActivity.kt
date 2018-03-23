package de.markusressel.datamunch.view.fragment.sharing.afp

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AfpSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class AfpShareDetailActivity : DetailActivityBase<AfpShareEntity>() {

    @Inject
    lateinit var persistenceHandler: AfpSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<AfpShareEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().afp_name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::AfpShareDetailContentFragment)


}