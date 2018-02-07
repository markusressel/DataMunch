package de.markusressel.freenaswebapiclient.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class JailConfigurationModel(val id: Long, val jc_collectionurl: String,
                             val jc_ipv4_network: String, val jc_ipv4_network_end: String,
                             val jc_ipv4_network_start: String, val jc_ipv6_network: String,
                             val jc_ipv6_network_end: String, val jc_ipv6_network_start: String,
                             val jc_path: String) {

    class Deserializer : ResponseDeserializable<List<JailConfigurationModel>> {
        override fun deserialize(content: String): List<JailConfigurationModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}