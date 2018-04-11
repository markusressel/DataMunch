/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenasrestapiclient.library.sharing.afp.AfpShareModel
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