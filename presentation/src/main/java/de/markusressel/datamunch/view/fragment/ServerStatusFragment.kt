package de.markusressel.datamunch.view.fragment

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.github.ajalt.timberkt.Timber
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.plugin.LoadingPlugin
import de.markusressel.datamunch.view.plugin.OptionsMenuPlugin
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_server_status.*
import java.util.concurrent.CancellationException
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServerStatusFragment : DaggerSupportFragmentBase() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var openWrtServerManager: de.markusressel.datamunch.data.openwrt.OpenWRTServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_server_status

    val loadingPlugin = LoadingPlugin()

    init {
        addFragmentPlugins(loadingPlugin,
                           OptionsMenuPlugin(optionsMenuRes = R.menu.options_menu_server_status,
                                             onCreateOptionsMenu = { menu: Menu?, menuInflater: MenuInflater? ->
                                                 // set refresh icon
                                                 val refreshIcon = iconHandler
                                                         .getOptionsMenuIcon(
                                                                 MaterialDesignIconic.Icon.gmi_refresh)
                                                 menu
                                                         ?.findItem(R.id.refresh)
                                                         ?.icon = refreshIcon
                                             }, onOptionsMenuItemClicked = {
                               when {
                                   it.itemId == R.id.refresh -> {
                                       reload()
                                       true
                                   }
                                   else -> false
                               }
                           }))
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())

        openWrtServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy())

        reload()
    }

    private fun reload() {
        loadingPlugin
                .showLoading()

        Single
                .fromCallable {
                    frittenbudeServerManager
                            .retrieveHostname()
                }
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    serverName
                            .text = it

                    loadingPlugin
                            .showContent()
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "Request cancelled" }
                    } else {
                        serverName
                                .text = it
                                .message
                        loadingPlugin
                                .showError(it)
                    }
                })

        Single
                .fromCallable {
                    frittenbudeServerManager
                            .retrieveUptime()
                }
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    val text = "Uptime: ${it.uptime}\n" + "Clock: ${it.clock}\n" + "Users: ${it.users}\n" + "Load 1m: ${it.load1m}\n" + "Load 5m: ${it.load5m}\n" + "Load 15m: ${it.load15m}\n"

                    serverStatus
                            .text = text

                    loadingPlugin
                            .showContent()
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "Request cancelled" }
                    } else {
                        serverStatus
                                .text = it
                                .message
                        loadingPlugin
                                .showError(it)
                    }
                })
    }

}