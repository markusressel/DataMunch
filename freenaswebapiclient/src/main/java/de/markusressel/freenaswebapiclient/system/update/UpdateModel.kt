package de.markusressel.freenaswebapiclient.system.update

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class UpdateModel(val name: String, val operation: String) {

    class SingleDeserializer : ResponseDeserializable<UpdateModel> {
        override fun deserialize(content: String): UpdateModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<UpdateModel>> {

        override fun deserialize(content: String): List<UpdateModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}