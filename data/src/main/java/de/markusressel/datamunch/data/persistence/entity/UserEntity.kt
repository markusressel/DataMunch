package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.account.user.UserModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 07.02.2018.
 */
@Entity
data class UserEntity(@Id var entityId: Long, val id: Long,
        //                      val bsdusr_attributes: Map<String, String>,
                      val bsdusr_builtin: Boolean, val bsdusr_email: String,
                      val bsdusr_full_name: String, val bsdusr_group: Long, val bsdusr_home: String,
                      val bsdusr_locked: Boolean, val bsdusr_password_disabled: Boolean,
                      val bsdusr_shell: String, val bsdusr_smbhash: String, val bsdusr_uid: Long,
                      val bsdusr_sshpubkey: String, val bsdusr_unixhash: String,
                      val bsdusr_username: String, val bsdusr_sudo: Boolean)

fun UserModel.asEntity(): UserEntity {
    return UserEntity(0, this.id,
            //                      this.bsdusr_attributes,
                      this.bsdusr_builtin, this.bsdusr_email, this.bsdusr_full_name,
                      this.bsdusr_group, this.bsdusr_home, this.bsdusr_locked,
                      this.bsdusr_password_disabled, this.bsdusr_shell, this.bsdusr_smbhash,
                      this.bsdusr_uid, this.bsdusr_sshpubkey, this.bsdusr_unixhash,
                      this.bsdusr_username, this.bsdusr_sudo)
}