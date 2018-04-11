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

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class ChildVolumeEntity(@Id var entityId: Long, val id: Long, val status: String,
                             val vol_guid: Long, val used: String, val name: String,
                             val used_pct: String, val used_si: String?,
                             val vol_encryptkey: String?, val vol_name: String?,
                             val decrypted: Boolean, val avail_si: String?, val mountpoint: String,
                             val vol_encrypt: Long, val total_si: String?)

fun ChildVolumeEntity.asEntity(): VolumeEntity {
    return VolumeEntity(0, this.id, this.status, this.vol_guid, this.used, this.name, this.used_pct,
                        this.used_si, this.vol_encryptkey, this.vol_name, this.decrypted,
                        this.avail_si, this.mountpoint, this.vol_encrypt, this.total_si)
}