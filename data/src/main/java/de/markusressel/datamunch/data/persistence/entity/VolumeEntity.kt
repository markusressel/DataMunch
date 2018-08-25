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

import de.markusressel.datamunch.data.IdentifiableListItem
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.library.storage.volume.VolumeModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class VolumeEntity(@Id var entityId: Long = 0, val id: Long = 0, val status: String = "",
                        val vol_guid: Long = 0, val used: String = "", val name: String = "",
                        val used_pct: String = "", val used_si: String? = null,
                        val vol_encryptkey: String? = null,
                        val vol_name: String? = null, val decrypted: Boolean = false,
                        val avail_si: String? = null,
                        val mountpoint: String = "", val vol_encrypt: Long = 0,
                        val total_si: String? = null) :
    IdentifiableListItem, SearchableListItem {

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(name, status)
    }

    lateinit var children: ToMany<VolumeEntity>

}

fun VolumeModel.asEntity(): VolumeEntity {
    val volumeEntity = VolumeEntity(0, this.id, this.status, this.vol_guid, this.used, this.name,
                                    this.used_pct, this.used_si, this.vol_encryptkey, this.vol_name,
                                    this.is_decrypted, this.avail_si, this.mountpoint,
                                    this.vol_encrypt,
                                    this.total_si)
    volumeEntity
            .children
            .addAll(this.children?.map {
                it
                        .asEntity()
            }
                            ?: emptyList())

    return volumeEntity
}