package de.markusressel.freenaswebapiclient.storage.scrub

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class ScrubModel(val scrub_threshold: Int, val scrub_dayweek: String, val scrub_enabled: Boolean,
                 val scrub_minute: String, val scrub_hour: String, val scrub_month: String,
                 val scrub_daymonth: String, val scrub_description: String, val id: Long,
                 val scrub_volume: String) {

    class SingleDeserializer : ResponseDeserializable<ScrubModel> {
        override fun deserialize(content: String): ScrubModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<ScrubModel>> {

        override fun deserialize(content: String): List<ScrubModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}