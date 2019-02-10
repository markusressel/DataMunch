/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import com.github.ajalt.timberkt.Timber
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.openwrt.OpenWRTServerManager
import de.markusressel.datamunch.view.component.LoadingComponent
import de.markusressel.datamunch.view.component.OptionsMenuComponent
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
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
    lateinit var openWrtServerManager: OpenWRTServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_server_status

    private val loadingComponent by lazy { LoadingComponent(this) }

    private val optionsMenuComponent: OptionsMenuComponent by lazy {
        OptionsMenuComponent(this,
                             optionsMenuRes = R.menu.options_menu_server_status,
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
        })
    }

    override fun initComponents(context: Context) {
        super
                .initComponents(context)
        loadingComponent
        optionsMenuComponent
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super
                .onCreateOptionsMenu(menu, inflater)
        optionsMenuComponent
                .onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (super.onOptionsItemSelected(item)) {
            return true
        }
        return optionsMenuComponent
                .onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        return loadingComponent
                .onCreateView(inflater, parent, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        openWrtServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy())

        reload()
    }

    private fun reload() {
        loadingComponent
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

                    loadingComponent
                            .showContent()
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "Request cancelled" }
                    } else {
                        serverName
                                .text = it
                                .message
                        loadingComponent
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

                    loadingComponent
                            .showContent()
                }, onError = {
                    if (it is CancellationException) {
                        Timber
                                .d { "Request cancelled" }
                    } else {
                        serverStatus
                                .text = it
                                .message
                        loadingComponent
                                .showError(it)
                    }
                })
    }
}
