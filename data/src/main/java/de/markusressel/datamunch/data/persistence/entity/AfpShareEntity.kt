package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.sharing.afp.AfpShareModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class AfpShareEntity(@Id var entityId: Long, val id: Long, val afp_upriv: Boolean,
                          val afp_comment: String, val afp_fperm: String, val afp_deny: String,
                          val afp_nostat: Boolean, val afp_name: String, val afp_nodev: Boolean,
                          val afp_rw: String, val afp_allow: String, val afp_dperm: String,
                          val afp_ro: String, val afp_sharepw: String, val afp_path: String,
                          val afp_timemachine: Boolean, val afp_umask: String)

fun AfpShareModel.asEntity(): AfpShareEntity {
    return AfpShareEntity(0, this.id, this.afp_upriv, this.afp_comment, this.afp_fperm,
                          this.afp_deny, this.afp_nostat, this.afp_name, this.afp_nodev,
                          this.afp_rw, this.afp_allow, this.afp_dperm, this.afp_ro,
                          this.afp_sharepw, this.afp_path, this.afp_timemachine, this.afp_umask)
}