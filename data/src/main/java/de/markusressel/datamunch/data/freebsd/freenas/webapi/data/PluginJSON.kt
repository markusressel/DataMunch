package de.markusressel.datamunch.data.freebsd.freenas.webapi.data

/**
 * Created by Markus on 29.01.2018.
 */
data class PluginJSON(
        val id: Int,
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
)