package de.markusressel.freenaswebapiclient.plugins

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class PluginModel(
        val id: Long,
        val plugin_api_version: Int,
        val plugin_arch: String,
        val plugin_enabled: Boolean,
        val plugin_ip: String,
        val plugin_jail: String,
        val plugin_name: String,
        val plugin_path: String,
        val plugin_pbiname: String,
        val plugin_port: Int,
        val plugin_version: String
) {

    class Deserializer : ResponseDeserializable<List<PluginModel>> {
        override fun deserialize(content: String): List<PluginModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson().fromJson(content)
        }

    }

}