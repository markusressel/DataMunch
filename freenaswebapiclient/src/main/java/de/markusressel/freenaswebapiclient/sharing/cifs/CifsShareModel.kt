package de.markusressel.freenaswebapiclient.sharing.cifs

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class CifsShareModel(val cifs_hostsallow: String, val cifs_name: String, val cifs_home: Boolean,
                     val cifs_default_permissions: Boolean, val cifs_guestok: Boolean,
                     val cifs_showhiddenfiles: Boolean, val cifs_hostsdeny: String,
                     val cifs_recyclebin: Boolean, val cifs_auxsmbconf: String,
                     val cifs_comment: String, val cifs_path: String, val cifs_ro: Boolean,
                     val cifs_guestonly: Boolean, val id: Long, val cifs_browsable: Boolean) {

    class SingleDeserializer : ResponseDeserializable<CifsShareModel> {
        override fun deserialize(content: String): CifsShareModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<CifsShareModel>> {
        override fun deserialize(content: String): List<CifsShareModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}