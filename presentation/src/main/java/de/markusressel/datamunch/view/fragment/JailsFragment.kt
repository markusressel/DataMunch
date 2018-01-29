package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.github.nitrico.lastadapter.LastAdapter
import de.markusressel.datamunch.BR
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.freebsd.freenas.webapi.data.JailJSON
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.databinding.ListItemJailBinding
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.view.fragment.base.LoadingSupportFragmentBase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_jails.*
import java.util.*
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class JailsFragment : LoadingSupportFragmentBase() {

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    private val currentJails: MutableList<JailJSON> = ArrayList()
    private lateinit var recyclerViewAdapter: LastAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_jails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = LastAdapter(currentJails, BR.item)
                .map<JailJSON, ListItemJailBinding>(R.layout.list_item_jail) {
                    onCreate { it.binding.presenter = this@JailsFragment }
                    onClick { openJailDetailView(currentJails[it.adapterPosition]) }
                }
                .into(recyclerview)

        recyclerview.adapter = recyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerview.layoutManager = layoutManager

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

        updateJailList()
    }

    private fun updateJailList() {
        showLoading()

        Single.fromCallable {
            frittenbudeServerManager.retrieveJails()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            currentJails.clear()
                            currentJails.addAll(it.sortedBy {
                                it.jail_host
                            })
                            recyclerViewAdapter.notifyDataSetChanged()

                            showContent()
                        },
                        onError = {
                            showError(it)
                        }
                )
    }

    fun startJail(jail: JailJSON) {
        Single.fromCallable {
            frittenbudeServerManager.startJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            updateJailList()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun stopJail(jail: JailJSON) {
        Single.fromCallable {
            frittenbudeServerManager.stopJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            updateJailList()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    fun restartJail(jail: JailJSON) {
        Single.fromCallable {
            frittenbudeServerManager.restartJail(jail)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()
                            updateJailList()
                        },
                        onError = {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_LONG).show()
                            showError(it)
                        }
                )
    }

    private fun openJailDetailView(jail: JailJSON) {

    }

}