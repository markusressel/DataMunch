package de.markusressel.freenaswebapiclient.tasks.smart

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 23.02.2018.
 */
class SMARTTaskModel(

        val id: Long, val smarttest_dayweek: String, val smarttest_daymonth: String,
        val smarttest_disks: Array<String>, val smarttest_month: String, val smarttest_type: String,
        val smarttest_hour: String, val smarttest_desc: String) {

    class SingleDeserializer : ResponseDeserializable<SMARTTaskModel> {
        override fun deserialize(content: String): SMARTTaskModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<SMARTTaskModel>> {

        override fun deserialize(content: String): List<SMARTTaskModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}