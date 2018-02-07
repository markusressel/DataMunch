package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.fragment.base.LoadingSupportFragmentBase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_server_status.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServerStatusFragment : LoadingSupportFragmentBase() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    @Inject
    lateinit var openWrtServerManager: de.markusressel.datamunch.data.openwrt.OpenWRTServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_server_status

    override val optionsMenuRes: Int?
        get() = R.menu.options_menu_server_status

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        val user = UserEntity(0, "Markus", "Markus Ressel", "mail@markusressel.de", 0)

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(), connectionManager.getMainSSHConnection())

        openWrtServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy())

        reload()
    }

    private fun reload() {
        showLoading()

        Single
                .fromCallable {
                    frittenbudeServerManager
                            .retrieveHostname()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    serverName
                            .text = it

                    showContent()
                }, onError = {
                    serverName
                            .text = it
                            .message
                    showError(it)
                })

        Single
                .fromCallable {
                    frittenbudeServerManager
                            .retrieveUptime()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    val text = "Uptime: ${it.uptime}\n" + "Clock: ${it.clock}\n" + "Users: ${it.users}\n" + "Load 1m: ${it.load1m}\n" + "Load 5m: ${it.load5m}\n" + "Load 15m: ${it.load15m}\n"

                    serverStatus
                            .text = text

                    showContent()
                }, onError = {
                    serverStatus
                            .text = it
                            .message
                    showError(it)
                })
    }

    override fun onOptionsMenuItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.refresh -> {
                reload()
                true
            }
            else -> false
        }
    }

}