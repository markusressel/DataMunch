package de.markusressel.freenaswebapiclient.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class MountpointModel(val id: Long, val destination: String, val jail: String, val mounted: Boolean,
                      val readonly: Boolean, val source: String) {

    class SingleDeserializer : ResponseDeserializable<MountpointModel> {
        override fun deserialize(content: String): MountpointModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<MountpointModel>> {
        override fun deserialize(content: String): List<MountpointModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }
    }

}