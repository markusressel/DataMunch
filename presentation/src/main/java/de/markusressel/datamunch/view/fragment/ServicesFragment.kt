package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.ServiceJSON
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.databinding.ListItemServiceBinding
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServicesFragment : ListFragmentBase<ServiceJSON>() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<ServiceJSON, ListItemServiceBinding>(R.layout.list_item_service) {
                    onCreate {
                        it.binding.setVariable(BR.presenter, this@ServicesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerview)
    }

    override fun loadListData(): List<ServiceJSON> {
        return frittenbudeServerManager.retrieveServices().sortedBy {
            it.srv_service
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