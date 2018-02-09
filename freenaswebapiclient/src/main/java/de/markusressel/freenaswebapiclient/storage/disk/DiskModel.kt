package de.markusressel.freenaswebapiclient.storage.disk

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class DiskModel(val disk_acousticlevel: String, val disk_advpowermgmt: String,
                val disk_serial: String, val disk_size: Long, val disk_multipath_name: String,
                val disk_identifier: String, val disk_togglesmart: Boolean,
                val disk_hddstandby: String, val disk_transfermode: String,
                val disk_multipath_member: String, val disk_description: String,
                val disk_smartoptions: String, val disk_expiretime: Long?, val disk_name: String

) {

    class SingleDeserializer : ResponseDeserializable<DiskModel> {
        override fun deserialize(content: String): DiskModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<DiskModel>> {

        override fun deserialize(content: String): List<DiskModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}