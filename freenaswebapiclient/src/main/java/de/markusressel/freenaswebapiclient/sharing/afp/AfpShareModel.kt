package de.markusressel.freenaswebapiclient.sharing.afp

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class AfpShareModel(val afp_upriv: Boolean, val id: Long, val afp_comment: String,
                    val afp_fperm: String, val afp_deny: String, val afp_nostat: Boolean,
                    val afp_name: String, val afp_nodev: Boolean, val afp_rw: String,
                    val afp_allow: String, val afp_dperm: String, val afp_ro: String,
                    val afp_sharepw: String, val afp_path: String, val afp_timemachine: Boolean,
                    val afp_umask: String) {

    class SingleDeserializer : ResponseDeserializable<AfpShareModel> {
        override fun deserialize(content: String): AfpShareModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<AfpShareModel>> {
        override fun deserialize(content: String): List<AfpShareModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}