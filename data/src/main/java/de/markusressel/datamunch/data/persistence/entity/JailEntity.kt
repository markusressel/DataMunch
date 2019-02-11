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

import de.markusressel.datamunch.data.EntityWithId
import de.markusressel.freenasrestapiclient.api.v1.jails.jail.JailModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class JailEntity(@Id var entityId: Long, val id: Long, val jail_alias_bridge_ipv4: String?,
                      val jail_alias_bridge_ipv6: String?, val jail_alias_ipv4: String?,
                      val jail_alias_ipv6: String?, val jail_autostart: Boolean,
                      val jail_bridge_ipv4: String?, val jail_bridge_ipv4_netmask: String?,
                      val jail_bridge_ipv6: String?, val jail_bridge_ipv6_prefix: String?,
                      val jail_defaultrouter_ipv4: String?, val jail_defaultrouter_ipv6: String?,
                      val jail_flags: String?, val jail_host: String, val jail_iface: String,
                      val jail_ipv4: String?, val jail_ipv4_netmask: String?,
                      val jail_ipv6: String?, val jail_ipv6_prefix: String?, val jail_mac: String?,
                      val jail_nat: Boolean, val jail_status: String?, val jail_type: String?,
                      val jail_vnet: Boolean) : EntityWithId {

    override fun getDbEntityId(): Long = entityId
    override fun setDbEntityId(id: Long) {
        entityId = id
    }

    override fun getItemId(): Long = id

}

fun JailModel.asEntity(entityId: Long = 0): JailEntity {
    return JailEntity(entityId, this.id, this.jail_alias_bridge_ipv4, this.jail_alias_bridge_ipv6,
                      this.jail_alias_ipv4, this.jail_alias_ipv6, this.jail_autostart,
                      this.jail_bridge_ipv4, this.jail_bridge_ipv4_netmask, this.jail_bridge_ipv6,
                      this.jail_bridge_ipv6_prefix, this.jail_defaultrouter_ipv4,
                      this.jail_defaultrouter_ipv6, this.jail_flags, this.jail_host,
                      this.jail_iface, this.jail_ipv4, this.jail_ipv4_netmask, this.jail_ipv6,
                      this.jail_ipv6_prefix, this.jail_mac, this.jail_nat, this.jail_status,
                      this.jail_type, this.jail_vnet)
}

fun JailEntity.isRunning(): Boolean {
    return "Running" == this.jail_status
}

fun JailEntity.isStopped(): Boolean {
    return "Stopped" == this.jail_status
}