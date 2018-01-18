package de.markusressel.datamunch.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.FreeBSDServerManager
import de.markusressel.datamunch.data.entity.Jail
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.view.adapter.JailRecyclerViewAdapter
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
class JailsFragment : DaggerSupportFragmentBase() {

    @Inject
    lateinit var applicationContext: Context

    @Inject
    lateinit var freeBSDServerManager: FreeBSDServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_jails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentJails: MutableList<Jail> = ArrayList()
        val jailRecyclerViewAdapter = JailRecyclerViewAdapter(applicationContext, currentJails)

        recyclerviewJails.adapter = jailRecyclerViewAdapter
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerviewJails.layoutManager = layoutManager

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

        // TODO: Show loading animation

        Single.fromCallable {
            freeBSDServerManager.retrieveJails(
                    turrisSshConnectionConfig,
                    frittenbudeSshConnectionConfig)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            currentJails.clear()
                            currentJails.addAll(it)
                            jailRecyclerViewAdapter.notifyDataSetChanged()
                        },
                        onError = {
                            // TODO: Show error message
                            Timber.e(it)
                        }
                )
    }

}