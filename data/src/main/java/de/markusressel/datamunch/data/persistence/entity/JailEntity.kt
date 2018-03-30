package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenasrestapiclient.library.jails.jail.JailModel
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
                      val jail_vnet: Boolean)

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