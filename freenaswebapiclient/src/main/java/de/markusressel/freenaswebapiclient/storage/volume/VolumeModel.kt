package de.markusressel.freenaswebapiclient.storage.volume

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class VolumeModel(val id: Long, val status: String, val vol_guid: Long, val used: String,
                  val name: String, val used_pct: String, val used_si: String?,
                  val vol_encryptkey: String?, val vol_name: String?, val is_decrypted: Boolean,
                  val avail_si: String?, val mountpoint: String, val vol_encrypt: Long,
                  val children: List<VolumeModel>?, val total_si: String?) {

    class SingleDeserializer : ResponseDeserializable<VolumeModel> {
        override fun deserialize(content: String): VolumeModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<VolumeModel>> {

        override fun deserialize(content: String): List<VolumeModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}