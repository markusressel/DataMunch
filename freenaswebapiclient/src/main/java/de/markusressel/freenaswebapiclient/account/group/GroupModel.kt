package de.markusressel.freenaswebapiclient.account.group

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class GroupModel(val id: Long, val bsdgrp_builtin: Boolean, val bsdgrp_gid: Long,
                 val bsdgrp_group: String, val bsdgrp_sudo: Boolean) {

    class SingleDeserializer : ResponseDeserializable<GroupModel> {
        override fun deserialize(content: String): GroupModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<GroupModel>> {
        override fun deserialize(content: String): List<GroupModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}