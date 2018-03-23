package de.markusressel.datamunch.view.fragment.plugins

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_disk_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class PluginDetailContentFragment : DetailContentFragmentBase<PluginEntity>() {

    @Inject
    protected lateinit var persistenceManager: PluginPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_plugin_plugin_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.id}"

        nameTextView
                .text = entity
                .plugin_name
    }

}