package de.markusressel.datamunch.view.fragment.jail.jail

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class JailDetailActivity : DetailActivityBase<JailEntity>() {

    @Inject
    lateinit var persistenceHandler: JailPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().jail_host

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::JailDetailContentFragment,
                       R.string.services to ::JailServicesContentFragment)


}