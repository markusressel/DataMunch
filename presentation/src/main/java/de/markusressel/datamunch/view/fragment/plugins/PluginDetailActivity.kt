package de.markusressel.datamunch.view.fragment.plugins

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class PluginDetailActivity : DetailActivityBase<PluginEntity>() {

    @Inject
    lateinit var persistenceHandler: PluginPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().plugin_name

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::PluginDetailContentFragment)


}