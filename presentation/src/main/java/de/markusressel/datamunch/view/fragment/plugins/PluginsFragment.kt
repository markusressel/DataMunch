package de.markusressel.datamunch.view.fragment.plugins

import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.databinding.ListItemPluginBinding
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.freenasrestapiclient.library.plugins.PluginModel
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_services.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class PluginsFragment : ListFragmentBase<PluginModel, PluginEntity>() {

    @Inject
    lateinit var persistenceManager: PluginPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> = persistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<PluginEntity, ListItemPluginBinding>(R.layout.list_item_plugin) {
                    onCreate {
                        it
                                .binding
                                .presenter = this@PluginsFragment
                    }
                    onClick {
                        openDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerView)
    }

    override fun loadListDataFromSource(): Single<List<PluginModel>> {
        return freeNasWebApiClient
                .getPlugins()
    }

    override fun mapToEntity(it: PluginModel): PluginEntity {
        return it
                .asEntity()
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

    private fun openDetailView(plugin: PluginEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(PluginDetailActivity::class.java, it,
                                               plugin.entityId)
                    startActivity(intent)
                }
    }

}
