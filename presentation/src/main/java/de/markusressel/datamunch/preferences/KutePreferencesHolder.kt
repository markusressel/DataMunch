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

package de.markusressel.datamunch.preferences

import android.content.Context
import com.eightbitlab.rxbus.Bus
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceDataProviderHolder
import de.markusressel.datamunch.event.LocaleChangedEvent
import de.markusressel.datamunch.event.ThemeChangedEvent
import de.markusressel.datamunch.view.IconHandler
import de.markusressel.kutepreferences.library.preference.category.KuteCategory
import de.markusressel.kutepreferences.library.preference.category.KuteDivider
import de.markusressel.kutepreferences.library.preference.number.KuteNumberPreference
import de.markusressel.kutepreferences.library.preference.select.KuteSingleSelectPreference
import de.markusressel.kutepreferences.library.preference.text.KutePasswordPreference
import de.markusressel.kutepreferences.library.preference.text.KuteTextPreference
import de.markusressel.kutepreferences.library.preference.toggle.KuteTogglePreference
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Holder for KutePreference items for easy access to preference values across the application
 */
@Singleton
class KutePreferencesHolder @Inject constructor(
        private val context: Context,
        private val iconHelper: IconHandler,
        private val dataProviderHolder: PreferenceDataProviderHolder) {

    val securityCategory by lazy {
        KuteCategory(key = R.string.category_security_key,
                     icon = iconHelper.getPreferenceIcon(MaterialDesignIconic.Icon.gmi_lock),
                     title = context.getString(R.string.category_security_title),
                     description = context.getString(R.string.category_security_description),
                     children = listOf(
                             useAppLockPreference
                     )
        )
    }

    val useAppLockPreference by lazy {
        KuteTogglePreference(key = R.string.lock_pattern_key,
                             icon = iconHelper.getPreferenceIcon(
                                     MaterialDesignIconic.Icon.gmi_smartphone_lock),
                             title = context.getString(R.string.use_pattern_lock_title),
                             defaultValue = false,
                             dataProvider = dataProviderHolder.dataProvider
        )
    }

    val connectionCategory by lazy {
        KuteCategory(key = R.string.category_connection_key,
                     icon = iconHelper.getPreferenceIcon(
                             MaterialDesignIconic.Icon.gmi_wifi),
                     title = context.getString(R.string.category_connection_title),
                     description = context.getString(R.string.category_connection_description),
                     children = listOf(
                             connectionUriPreference,
                             KuteDivider(
                                     key = R.string.divider_ssh_key,
                                     title = context.getString(R.string.divider_ssh_title)
                             ),
                             sshUserPreference,
                             sshPasswordPreference,
                             KuteDivider(
                                     key = R.string.divider_proxy_key,
                                     title = context.getString(R.string.divider_proxy_title)
                             ),
                             sshProxyHostPreference,
                             sshProxyPortPreference,
                             sshProxyUserPreference,
                             sshProxyPasswordPreference
                     )
        )
    }

    val connectionUriPreference by lazy {
        KuteTextPreference(key = R.string.connection_host_key,
                           icon = iconHelper.getPreferenceIcon(
                                   MaterialDesignIconic.Icon.gmi_battery),
                           title = context.getString(R.string.connection_host_title),
                           defaultValue = "",
                           dataProvider = dataProviderHolder.dataProvider)

    }

    val sshUserPreference by lazy {
        KuteTextPreference(key = R.string.connection_ssh_user_key,
                           title = context.getString(R.string.connection_ssh_user_title),
                           defaultValue = "root",
                           dataProvider = dataProviderHolder.dataProvider)

    }

    val sshPasswordPreference by lazy {
        KutePasswordPreference(key = R.string.connection_ssh_password_key,
                               title = context.getString(R.string.connection_ssh_password_summary),
                               defaultValue = "",
                               dataProvider = dataProviderHolder.dataProvider)

    }

    val sshProxyHostPreference by lazy {
        KuteTextPreference(key = R.string.connection_ssh_proxy_host_key,
                           title = context.getString(R.string.connection_ssh_proxy_host_title),
                           defaultValue = "",
                           dataProvider = dataProviderHolder.dataProvider)

    }

    val sshProxyPortPreference by lazy {
        KuteNumberPreference(key = R.string.connection_ssh_proxy_port_key,
                             title = context.getString(R.string.connection_ssh_proxy_port_title),
                             defaultValue = 22,
                             dataProvider = dataProviderHolder.dataProvider)

    }

    val sshProxyUserPreference by lazy {
        KuteTextPreference(key = R.string.connection_ssh_proxy_user_key,
                           title = context.getString(R.string.connection_ssh_proxy_user_title),
                           defaultValue = "",
                           dataProvider = dataProviderHolder.dataProvider)

    }

    val sshProxyPasswordPreference by lazy {
        KutePasswordPreference(key = R.string.connection_ssh_proxy_password_key,
                               icon = iconHelper.getPreferenceIcon(
                                       MaterialDesignIconic.Icon.gmi_lock),
                               title = context.getString(
                                       R.string.connection_ssh_proxy_password_title),
                               defaultValue = "",
                               dataProvider = dataProviderHolder.dataProvider)

    }

    val themePreference by lazy {
        KuteSingleSelectPreference(
                context = context,
                key = R.string.theme_key,
                icon = iconHelper.getPreferenceIcon(MaterialDesignIconic.Icon.gmi_colorize),
                title = context.getString(R.string.theme_title),
                possibleValues = mapOf(
                        R.string.theme_dark_value to R.string.theme_dark_value_name,
                        R.string.theme_light_value to R.string.theme_light_value_name
                ),
                defaultValue = R.string.theme_dark_value,
                dataProvider = dataProviderHolder.dataProvider,
                onPreferenceChangedListener = { old, new ->
                    Bus
                            .send(ThemeChangedEvent(new))
                }
        )
    }

    val languagePreference by lazy {
        KuteSingleSelectPreference(
                context = context,
                key = R.string.locale_key,
                icon = iconHelper.getPreferenceIcon(
                        MaterialDesignIconic.Icon.gmi_language_html5),
                title = context.getString(R.string.locale_title),
                defaultValue = R.string.locale_SYSTEM_value,
                possibleValues = mapOf(
                        R.string.locale_SYSTEM_value to R.string.locale_SYSTEM_value_name,
                        R.string.locale_DE_value to R.string.locale_DE_value_name,
                        R.string.locale_EN_value to R.string.locale_EN_value_name
                ),
                dataProvider = dataProviderHolder.dataProvider,
                onPreferenceChangedListener = { old, new ->
                    Bus
                            .send(LocaleChangedEvent(new))
                }
        )
    }

}

