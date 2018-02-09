package de.markusressel.freenaswebapiclient.storage.dataset

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class DatasetModel(val atime: String, val avail: Long, val comments: String?,
                   val compression: String, val dedup: String, val inherit_props: List<String>?,
                   val mountpoint: String, val name: String, val pool: String, val quota: Long,
                   val readonly: String, val recordsize: Long, val refer: Long, val refquota: Long,
                   val refreservation: Long, val reservation: Long, val used: Long) {

    class SingleDeserializer : ResponseDeserializable<DatasetModel> {
        override fun deserialize(content: String): DatasetModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<DatasetModel>> {

        override fun deserialize(content: String): List<DatasetModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}