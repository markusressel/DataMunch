package de.markusressel.freenaswebapiclient.sharing.nfs

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class NfsShareModel(val id: Long, val nfs_alldirs: Boolean, val nfs_comment: String,
                    val nfs_hosts: String, val nfs_mapall_group: String,
                    val nfs_mapall_user: String, val nfs_maproot_group: String,
                    val nfs_maproot_user: String, val nfs_network: String,
                    val nfs_paths: List<String>?, val nfs_quiet: Boolean, val nfs_ro: Boolean,
                    val nfs_security: List<String>?) {

    class SingleDeserializer : ResponseDeserializable<NfsShareModel> {
        override fun deserialize(content: String): NfsShareModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<NfsShareModel>> {
        override fun deserialize(content: String): List<NfsShareModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}