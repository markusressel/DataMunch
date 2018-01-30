package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.ServicePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ServiceEntity
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
class ServicesFragment : ListFragmentBase<ServiceEntity>() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var servicePersistenceManager: ServicePersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<ServiceEntity, ListItemServiceBinding>(R.layout.list_item_service) {
                    onCreate {
                        it.binding.setVariable(BR.presenter, this@ServicesFragment)
                    }
                    onClick {

                    }
                }
                .into(recyclerview)
    }

    override fun loadListDataFromSource(): List<ServiceEntity> {
        return frittenbudeServerManager.retrieveServices().map { it.newEntity() }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<ServiceEntity> {
        return servicePersistenceManager
    }

    override fun loadListDataFromPersistence(): List<ServiceEntity> {
        return super.loadListDataFromPersistence().sortedBy {
            it.srv_service
        }
    }

    override fun onListViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }

}