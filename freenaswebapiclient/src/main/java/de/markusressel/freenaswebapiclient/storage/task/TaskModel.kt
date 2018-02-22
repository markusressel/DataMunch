package de.markusressel.freenaswebapiclient.storage.task

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class TaskModel(val id: Long, val task_ret_count: Long, val task_repeat_unit: String,
                val task_enabled: Boolean, val task_recursive: Boolean, val task_end: String,
                val task_interval: Long, val task_byweekday: String, val task_begin: String,
                val task_filesystem: String, val task_ret_unit: String) {

    class SingleDeserializer : ResponseDeserializable<TaskModel> {
        override fun deserialize(content: String): TaskModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<TaskModel>> {

        override fun deserialize(content: String): List<TaskModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}