package de.markusressel.freenaswebapiclient.services.service

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class ServiceModel(
        val id: Long,
        val srv_service: String,
        val srv_enabled: Boolean
) {

    class Deserializer : ResponseDeserializable<List<ServiceModel>> {
        override fun deserialize(content: String): List<ServiceModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson().fromJson(content)
        }

    }

}