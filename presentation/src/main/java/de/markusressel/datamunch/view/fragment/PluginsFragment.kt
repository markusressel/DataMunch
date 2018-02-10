package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemPluginBinding
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_services.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class PluginsFragment : ListFragmentBase<PluginEntity>() {

    @Inject
    lateinit var pluginPersistenceManager: PluginPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<PluginEntity, ListItemPluginBinding>(R.layout.list_item_plugin) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@PluginsFragment
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): List<PluginEntity> {
        return freeNasWebApiClient
                .getPlugins()
                .blockingGet()
                .map {
                    it
                            .asEntity()
                }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> {
        return pluginPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<PluginEntity> {
        return super
                .loadListDataFromPersistence()
                .sortedBy {
                    it
                            .plugin_name
                            .toLowerCase()
                }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
    }

}