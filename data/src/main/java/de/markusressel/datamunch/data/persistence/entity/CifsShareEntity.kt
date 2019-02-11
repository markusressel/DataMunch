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

import de.markusressel.datamunch.data.EntityWithId
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.api.v1.sharing.cifs.CifsShareModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class CifsShareEntity(@Id var entityId: Long, val id: Long, val cifs_hostsallow: String,
                           val cifs_name: String, val cifs_home: Boolean,
                           val cifs_default_permissions: Boolean, val cifs_guestok: Boolean,
                           val cifs_showhiddenfiles: Boolean, val cifs_hostsdeny: String,
                           val cifs_recyclebin: Boolean, val cifs_auxsmbconf: String,
                           val cifs_comment: String, val cifs_path: String, val cifs_ro: Boolean,
                           val cifs_guestonly: Boolean, val cifs_browsable: Boolean) : EntityWithId, SearchableListItem {

    override fun getDbEntityId(): Long = entityId
    override fun setDbEntityId(id: Long) {
        entityId = id
    }

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(cifs_name)
    }

}

fun CifsShareModel.asEntity(): CifsShareEntity {
    return CifsShareEntity(0, this.id, this.cifs_hostsallow, this.cifs_name, this.cifs_home,
            this.cifs_default_permissions, this.cifs_guestok,
            this.cifs_showhiddenfiles, this.cifs_hostsdeny, this.cifs_recyclebin,
            this.cifs_auxsmbconf, this.cifs_comment, this.cifs_path, this.cifs_ro,
            this.cifs_guestonly, this.cifs_browsable)
}