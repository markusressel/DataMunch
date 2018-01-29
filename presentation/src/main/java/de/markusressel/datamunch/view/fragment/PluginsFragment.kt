package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.PluginJSON
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.databinding.ListItemPluginBinding
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_services.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class PluginsFragment : ListFragmentBase<PluginJSON>() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<PluginJSON, ListItemPluginBinding>(R.layout.list_item_plugin) {
                    onCreate { it.binding.presenter = this@PluginsFragment }
                }
                .into(recyclerview)
    }

    override fun loadListData(): List<PluginJSON> {
        return frittenbudeServerManager.retrievePlugins().sortedBy {
            it.plugin_name
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val frittenbudeSshConnectionConfig = SSHConnectionConfig(
                host = preferenceHandler.getValue(PreferenceHandler.CONNECTION_HOST),
                username = preferenceHandler.getValue(PreferenceHandler.SSH_USER),
                password = preferenceHandler.getValue(PreferenceHandler.SSH_PASS)
        )

        val turrisSshConnectionConfig = SSHConnectionConfig(
                host = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_HOST),
                port = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_PORT),
                username = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_USER),
                password = preferenceHandler.getValue(PreferenceHandler.SSH_PROXY_PASSWORD)
        )

        frittenbudeServerManager.setSSHConnectionConfig(
                turrisSshConnectionConfig,
                frittenbudeSshConnectionConfig
        )

        updateListData()
    }

}