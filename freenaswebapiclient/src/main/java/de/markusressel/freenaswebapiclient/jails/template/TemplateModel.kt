package de.markusressel.freenaswebapiclient.jails.template

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class TemplateModel(val id: Long, val jt_arch: String, val jt_instances: Long, val jt_name: String,
                    val jt_os: String, val jt_url: String) {

    class SingleDeserializer : ResponseDeserializable<TemplateModel> {
        override fun deserialize(content: String): TemplateModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<TemplateModel>> {
        override fun deserialize(content: String): List<TemplateModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}