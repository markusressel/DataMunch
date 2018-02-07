package de.markusressel.freenaswebapiclient.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class JailModel(
        val id: Long,
        val jail_alias_bridge_ipv4: String?,
        val jail_alias_bridge_ipv6: String?,
        val jail_alias_ipv4: String?,
        val jail_alias_ipv6: String?,
        val jail_autostart: Boolean,
        val jail_bridge_ipv4: String?,
        val jail_bridge_ipv4_netmask: String?,
        val jail_bridge_ipv6: String?,
        val jail_bridge_ipv6_prefix: String?,
        val jail_defaultrouter_ipv4: String?,
        val jail_defaultrouter_ipv6: String?,
        val jail_flags: String?,
        val jail_host: String,
        val jail_iface: String,
        val jail_ipv4: String?,
        val jail_ipv4_netmask: String?,
        val jail_ipv6: String?,
        val jail_ipv6_prefix: String?,
        val jail_mac: String?,
        val jail_nat: Boolean,
        val jail_status: String?,
        val jail_type: String?,
        val jail_vnet: Boolean
) {

    class ListDeserializer : ResponseDeserializable<List<JailModel>> {
        override fun deserialize(content: String): List<JailModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson().fromJson(content)
        }

    }

}