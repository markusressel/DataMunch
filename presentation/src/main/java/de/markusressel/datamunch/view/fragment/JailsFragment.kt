package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.databinding.ListItemJailBinding
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jails.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class JailsFragment : ListFragmentBase<JailEntity>() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var jailPersistenceManager: JailPersistenceManager

    override fun createAdapter(): LastAdapter {
        return LastAdapter(listValues, BR.item)
                .map<JailEntity, ListItemJailBinding>(R.layout.list_item_jail) {
                    onCreate { it.binding.presenter = this@JailsFragment }
                    onClick {
                        openJailDetailView(listValues[it.adapterPosition])
                    }
                }
                .into(recyclerview)
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

    override fun loadListDataFromSource(): List<JailEntity> {
        // retrieve Jails from server
        return frittenbudeServerManager.retrieveJails().map {
            it.newEntity()
        }
    }

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> {
        return jailPersistenceManager
    }

    override fun loadListDataFromPersistence(): List<JailEntity> {
        return super.loadListDataFromPersistence().sortedBy {
            it.jail_host
        }
    }

    fun startJail(jail: JailEntity) {
        Single.fromCallable {
            frittenbudeServerManager.startJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun stopJail(jail: JailEntity) {
        Single.fromCallable {
            frittenbudeServerManager.stopJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun restartJail(jail: JailEntity) {
        Single.fromCallable {
            frittenbudeServerManager.restartJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            reloadDataFromSource()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    private fun openJailDetailView(jail: JailEntity) {

    }

}