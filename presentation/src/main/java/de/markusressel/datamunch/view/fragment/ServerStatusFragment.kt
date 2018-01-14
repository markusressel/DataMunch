package de.markusressel.datamunch.view.fragment

import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.ServerManager
import de.markusressel.datamunch.data.entity.Server
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.CONNECTION_HOST
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_PASS
import de.markusressel.datamunch.data.preferences.PreferenceHandler.Companion.SSH_USER
import de.markusressel.datamunch.domain.SSHCredentials
import de.markusressel.datamunch.domain.User
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
class ServerStatusFragment : DaggerSupportFragmentBase() {

    @Inject
    lateinit var serverManager: ServerManager

    override val layoutRes: Int
        get() = R.layout.fragment_server_status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = User(0, "Markus", "Markus Ressel", "mail@markusressel.de", 0)

        val server = Server(0, "Frittenbude", preferenceHandler.getValue(CONNECTION_HOST))

        val username = preferenceHandler.getValue(SSH_USER)
        val password = preferenceHandler.getValue(SSH_PASS)

        val sshCredentials = SSHCredentials(username, password)

        Single.fromCallable {
            serverManager.retrieveJails(
                    server = server,
                    sshCredentials = sshCredentials
            )
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
            serverManager.retrieveUptime(
                    server = server,
                    sshCredentials = sshCredentials
            )
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            val text = "Uptime: ${it.uptime}\n" +
                                    "Clock: ${it.clock}\n" +
                                    "Users: ${it.users}\n" +
                                    "Load 1m: ${it.loadAverage1}\n" +
                                    "Load 5m: ${it.loadAverage5}\n" +
                                    "Load 15m: ${it.loadAverage15}\n"

                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
                        },
                        onError = {
                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
                            Timber.e(it)
                        }
                )

    }


}