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

package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.datamunch.data.IdentifiableListItem
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.library.plugins.PluginModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class PluginEntity(@Id var entityId: Long, val id: Long, val plugin_api_version: Int,
                        val plugin_arch: String, val plugin_enabled: Boolean, val plugin_ip: String,
                        val plugin_jail: String, val plugin_name: String, val plugin_path: String,
                        val plugin_pbiname: String, val plugin_port: Int,
                        val plugin_version: String) : IdentifiableListItem, SearchableListItem {

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(plugin_name)
    }

}

fun PluginModel.asEntity(): PluginEntity {
    return PluginEntity(0, this.id, this.plugin_api_version, this.plugin_arch, this.plugin_enabled, this.plugin_ip, this.plugin_jail, this.plugin_name, this.plugin_path, this.plugin_pbiname, this.plugin_port, this.plugin_version)
}