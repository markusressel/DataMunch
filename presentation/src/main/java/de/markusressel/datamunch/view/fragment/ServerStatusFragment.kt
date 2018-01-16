package de.markusressel.datamunch.view.fragment

import android.Manifest
import android.os.Bundle
import android.os.Environment
import com.github.ajalt.timberkt.Timber
import com.tbruyelle.rxpermissions2.RxPermissions
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.FreeBSDServerManager
import de.markusressel.datamunch.data.OpenWRTServerManager
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.CONNECTION_HOST
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PASS
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PROXY_HOST
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PROXY_PASSWORD
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PROXY_PORT
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PROXY_USER
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_USER
import de.markusressel.datamunch.domain.SSHConnectionConfig
import de.markusressel.datamunch.domain.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_server_status.*
import java.io.File
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class ServerStatusFragment : DaggerSupportFragmentBase() {

    @Inject
    lateinit var freeBSDServerManager: FreeBSDServerManager

    @Inject
    lateinit var openWrtServerManager: OpenWRTServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_server_status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = User(0, "Markus", "Markus Ressel", "mail@markusressel.de", 0)

        val frittenbudeSshConnectionConfig = SSHConnectionConfig(
                host = preferenceHandler.getValue(CONNECTION_HOST),
                username = preferenceHandler.getValue(SSH_USER),
                password = preferenceHandler.getValue(SSH_PASS)
        )

        val turrisSshConnectionConfig = SSHConnectionConfig(
                host = preferenceHandler.getValue(SSH_PROXY_HOST),
                port = preferenceHandler.getValue(SSH_PROXY_PORT),
                username = preferenceHandler.getValue(SSH_PROXY_USER),
                password = preferenceHandler.getValue(SSH_PROXY_PASSWORD)
        )

        Single.fromCallable {
            freeBSDServerManager.retrieveJails(frittenbudeSshConnectionConfig)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            var text = ""
                            for (jail in it) {
                                text += jail.toString() + "\n"
                            }

                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
                        },
                        onError = {
                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
                            Timber.e(it)
                        }
                )

        Single.fromCallable {
            openWrtServerManager.retrieveUptime(turrisSshConnectionConfig)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            val text = "Turris Uptime: ${it.uptime}\n" +
                                    "Clock: ${it.clock}\n" +
                                    "Users: ${it.users}\n" +
                                    "Load 1m: ${it.load1m}\n" +
                                    "Load 5m: ${it.load5m}\n" +
                                    "Load 15m: ${it.load15m}\n"

                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
                        },
                        onError = {
                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
                            Timber.e(it)
                        }
                )

        // tunneled connection
        Single.fromCallable {
            freeBSDServerManager.retrieveUptime(
                    turrisSshConnectionConfig,
                    frittenbudeSshConnectionConfig
            )
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            val text = "Uptime: ${it.uptime}\n" +
                                    "Clock: ${it.clock}\n" +
                                    "Users: ${it.users}\n" +
                                    "Load 1m: ${it.load1m}\n" +
                                    "Load 5m: ${it.load5m}\n" +
                                    "Load 15m: ${it.load15m}\n"

                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
                        },
                        onError = {
                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
                            Timber.e(it)
                        }
                )


        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath + File.separator
                + "Camera" + File.separator
                + "IMG_20180116_164959.jpg")
        val destinationPath = "/mnt/vol1/Media/Fotos/Markus/DataMunch/IMG_20180116_164959.jpg"

        val rxPermissions = RxPermissions(activity!!)

        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        Single.fromCallable {
                            freeBSDServerManager.uploadFile(
                                    turrisSshConnectionConfig,
                                    frittenbudeSshConnectionConfig,
                                    file = file,
                                    destinationPath = destinationPath
                            )
                        }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy(
                                        onSuccess = {
                                            val text = "Upload Success"

                                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
                                        },
                                        onError = {
                                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
                                            Timber.e(it)
                                        }
                                )
                    } else {
                        Timber.e { "Missing permission" }
                    }
                }

    }

}