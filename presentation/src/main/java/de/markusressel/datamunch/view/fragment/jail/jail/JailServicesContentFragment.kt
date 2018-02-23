package de.markusressel.datamunch.view.fragment.jail.jail

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.View
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Markus on 18.02.2018.
 */
class JailServicesContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jails_jail_services

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        frittenbudeServerManager
                .setSSHConnectionConfig(connectionManager.getSSHProxy(),
                                        connectionManager.getMainSSHConnection())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super
                .onResume()
        updateUi()
    }

    private fun updateUi() {
        Single
                .fromCallable {
                    frittenbudeServerManager
                            .executeInJail(getEntityFromPersistence().jail_host, "service -l")
                }
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    Timber
                            .d {
                                val services = it
                                        .last()
                                        .split(Regex.fromLiteral("\\r?\\n"))

                                services
                                        .joinToString(separator = ",")
                            }
                }, onError = {
                    Timber
                            .e(it)
                })
    }

}