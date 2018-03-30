package de.markusressel.datamunch.data.persistence.entity

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
                        val plugin_version: String)

fun PluginModel.asEntity(): PluginEntity {
    return PluginEntity(0, this.id, this.plugin_api_version, this.plugin_arch, this.plugin_enabled, this.plugin_ip, this.plugin_jail, this.plugin_name, this.plugin_path, this.plugin_pbiname, this.plugin_port, this.plugin_version)
}