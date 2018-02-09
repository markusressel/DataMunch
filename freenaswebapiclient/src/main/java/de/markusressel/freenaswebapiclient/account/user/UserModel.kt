package de.markusressel.freenaswebapiclient.account.user

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson

/**
 * Created by Markus on 06.02.2018.
 */
class UserModel(val id: Long,
        //                val bsdusr_attributes: Map<String, String>,
                val bsdusr_builtin: Boolean, val bsdusr_email: String, val bsdusr_full_name: String,
                val bsdusr_group: Long, val bsdusr_home: String, val bsdusr_locked: Boolean,
                val bsdusr_password_disabled: Boolean, val bsdusr_shell: String,
                val bsdusr_smbhash: String, val bsdusr_uid: Long, val bsdusr_sshpubkey: String,
                val bsdusr_unixhash: String, val bsdusr_username: String,
                val bsdusr_sudo: Boolean) {

    class SingleDeserializer : ResponseDeserializable<UserModel> {
        override fun deserialize(content: String): UserModel? {
            return Gson()
                    .fromJson(content)
        }
    }

    class ListDeserializer : ResponseDeserializable<List<UserModel>> {
        override fun deserialize(content: String): List<UserModel>? {
            if (content.isEmpty()) {
                return emptyList()
            }

            return Gson()
                    .fromJson(content)
        }

    }

}