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

package de.markusressel.datamunch.data.preferences

import android.content.Context
import de.markusressel.datamunch.data.R
import de.markusressel.typedpreferences.PreferenceItem
import de.markusressel.typedpreferences.PreferencesHandlerBase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Markus on 20.12.2017.
 */
@Singleton
class PreferenceHandler @Inject constructor(context: Context) : PreferencesHandlerBase(context) {

    // be sure to override the get() method
    override var sharedPreferencesName: String? = null

    override val allPreferenceItems: Set<PreferenceItem<*>> = hashSetOf(CONNECTION_HOST, SSH_USER,
                                                                        SSH_PASS, SSH_PROXY_HOST,
                                                                        SSH_PROXY_PORT,
                                                                        SSH_PROXY_USER,
                                                                        SSH_PROXY_PASSWORD,
                                                                        USE_PATTERN_LOCK,
                                                                        LOCK_PATTERN)

    companion object {
        val CONNECTION_HOST = PreferenceItem(R.string.connection_host_key, "")

        val SSH_USER = PreferenceItem(R.string.connection_ssh_user_key, "root")
        val SSH_PASS = PreferenceItem(R.string.connection_ssh_password_key,
                                      "")
        val SSH_PROXY_HOST = PreferenceItem(R.string.connection_ssh_proxy_host_key,
                                            "")
        val SSH_PROXY_PORT = PreferenceItem(R.string.connection_ssh_proxy_port_key, 48123)
        val SSH_PROXY_USER = PreferenceItem(R.string.connection_ssh_proxy_user_key, "root")
        val SSH_PROXY_PASSWORD = PreferenceItem(R.string.connection_ssh_proxy_password_key,
                                                "")

        val USE_PATTERN_LOCK = PreferenceItem(R.string.use_pattern_lock_key, false)
        val LOCK_PATTERN = PreferenceItem(R.string.lock_pattern_key,
                                          "")
    }

}