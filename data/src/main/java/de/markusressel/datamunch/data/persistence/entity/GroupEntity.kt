package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.account.group.GroupModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 07.02.2018.
 */
@Entity
data class GroupEntity(@Id var entityId: Long, val id: Long, val bsdgrp_builtin: Boolean,
                       val bsdgrp_gid: Long, val bsdgrp_group: String, val bsdgrp_sudo: Boolean)

fun GroupModel.asEntity(): GroupEntity {
    return GroupEntity(0, this.id, this.bsdgrp_builtin, this.bsdgrp_gid, this.bsdgrp_group,
                       this.bsdgrp_sudo)
}