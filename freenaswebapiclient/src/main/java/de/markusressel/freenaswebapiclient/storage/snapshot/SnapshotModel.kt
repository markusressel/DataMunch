package de.markusressel.freenaswebapiclient.storage.snapshot

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class SnapshotModel(val filesystem: String, val fullname: String, val id: String,
                    val mostrecent: Boolean, val name: String, val parent_type: String,
                    val refer: String, val used: String

) {

    class SingleDeserializer : ResponseDeserializable<SnapshotModel> {
        override fun deserialize(content: String): SnapshotModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<SnapshotModel>> {

        override fun deserialize(content: String): List<SnapshotModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}