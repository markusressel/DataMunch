package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.sharing.nfs.NfsShareModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class NfsShareEntity(@Id var entityId: Long, val id: Long, val nfs_alldirs: Boolean,
                          val nfs_comment: String, val nfs_hosts: String,
                          val nfs_mapall_group: String, val nfs_mapall_user: String,
                          val nfs_maproot_group: String, val nfs_maproot_user: String,
                          val nfs_network: String,
        // val nfs_paths: List<String>?,
                          val nfs_quiet: Boolean, val nfs_ro: Boolean
        // , val nfs_security: List<String>?
)

fun NfsShareModel.asEntity(): NfsShareEntity {
    return NfsShareEntity(0, this.id, this.nfs_alldirs, this.nfs_comment, this.nfs_hosts,
                          this.nfs_mapall_group, this.nfs_mapall_user, this.nfs_maproot_group,
                          this.nfs_maproot_user, this.nfs_network,
            // this.nfs_paths,
                          this.nfs_quiet, this.nfs_ro
            // , this.nfs_security
    )
}