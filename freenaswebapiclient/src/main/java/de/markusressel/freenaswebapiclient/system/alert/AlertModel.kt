package de.markusressel.freenaswebapiclient.system.alert

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class AlertModel(val id: String, val level: String, val message: String, val dismissed: Boolean) {

    class SingleDeserializer : ResponseDeserializable<AlertModel> {
        override fun deserialize(content: String): AlertModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<AlertModel>> {

        override fun deserialize(content: String): List<AlertModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}