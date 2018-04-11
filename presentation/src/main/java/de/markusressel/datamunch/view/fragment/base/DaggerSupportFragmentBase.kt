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
import de.markusressel.freenasrestapiclient.library.BasicAuthConfig
import de.markusressel.freenasrestapiclient.library.FreeNasWebApiClient
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

        return alternative
                ?: newContainer
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