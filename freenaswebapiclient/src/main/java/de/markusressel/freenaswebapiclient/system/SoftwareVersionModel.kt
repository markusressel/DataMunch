package de.markusressel.freenaswebapiclient.system

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class SoftwareVersionModel(val fullversion: String, val name: String, val version: String) {

    class SingleDeserializer : ResponseDeserializable<SoftwareVersionModel> {
        override fun deserialize(content: String): SoftwareVersionModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<SoftwareVersionModel>> {

        override fun deserialize(content: String): List<SoftwareVersionModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}