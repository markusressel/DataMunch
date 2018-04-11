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

import de.markusressel.freenasrestapiclient.library.storage.snapshot.SnapshotModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class SnapshotEntity(@Id var entityId: Long, val filesystem: String, val fullname: String,
                          val id: String, val mostrecent: Boolean, val name: String,
                          val parent_type: String, val refer: String, val used: String)

fun SnapshotModel.asEntity(): SnapshotEntity {
    return SnapshotEntity(0, this.filesystem, this.fullname, this.id, this.mostrecent, this.name,
                          this.parent_type, this.refer, this.used)
}