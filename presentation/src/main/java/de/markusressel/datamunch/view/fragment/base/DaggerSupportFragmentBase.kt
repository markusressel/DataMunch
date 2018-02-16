package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.data.ssh.ConnectionManager
import de.markusressel.datamunch.view.IconHandler
import de.markusressel.freenaswebapiclient.BasicAuthConfig
import de.markusressel.freenaswebapiclient.FreeNasWebApiClient
import io.reactivex.disposables.Disposable
import javax.inject.Inject


/**
 * Base class for implementing a fragment
 *
 * Created by Markus on 07.01.2018.
 */
abstract class DaggerSupportFragmentBase : LifecycleFragmentBase(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {
        AndroidSupportInjection
                .inject(this)
        super
                .onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    @Inject
    protected lateinit var connectionManager: ConnectionManager

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    @Inject
    protected lateinit var iconHandler: IconHandler

    val freeNasWebApiClient = FreeNasWebApiClient()

    // RxJava disposables that need to be disposed when the fragment is paused
    val disposables: MutableList<Disposable> = mutableListOf()

    /**
     * The layout resource for this Activity
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val newContainer = inflater.inflate(layoutRes, container, false) as ViewGroup

        val alternative = super
                .onCreateView(inflater, newContainer, savedInstanceState)

        if (alternative != null) {
            return alternative
        } else {
            return newContainer
        }
    }

    override fun onPause() {
        super
                .onPause()

        disposables
                .forEach {
                    it
                            .dispose()
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        freeNasWebApiClient
                .setHostname("frittenbude.markusressel.de")
        freeNasWebApiClient
                .setApiResource("frittenbudeapi")
        freeNasWebApiClient
                .setBasicAuthConfig(BasicAuthConfig(
                        username = connectionManager.getMainSSHConnection().username,
                        password = connectionManager.getMainSSHConnection().password))

        super
                .onViewCreated(view, savedInstanceState)
    }

}