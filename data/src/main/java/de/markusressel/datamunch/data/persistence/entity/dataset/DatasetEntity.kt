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

package de.markusressel.datamunch.data.persistence.entity.dataset

import de.markusressel.datamunch.data.EntityWithId
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.api.v1.storage.dataset.DatasetModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * Created by Markus on 30.01.2018.
 */

@Entity
data class DatasetEntity(@Id var entityId: Long, val atime: String, val avail: Long,
                         val comments: String?, val compression: String, val dedup: String,
                         val mountpoint: String, val name: String, val pool: String,
                         val quota: Long, val readonly: String, val recordsize: Long,
                         val refer: Long, val refquota: Long, val refreservation: Long,
                         val reservation: Long, val used: Long) :
        EntityWithId,
    SearchableListItem {

    override fun getDbEntityId(): Long = entityId
    override fun setDbEntityId(id: Long) {
        entityId = id
    }

    override fun getItemId(): Long = entityId

    override fun getSearchableContent(): List<Any> {
        return listOf(name)
    }

    lateinit var inherit_props: ToMany<InheritedProperty>

}

fun DatasetModel.asEntity(): DatasetEntity {
    val entity = DatasetEntity(0, this.atime, this.avail, this.comments, this.compression,
                               this.dedup,
                               this.mountpoint,
                               this.name, this.pool, this.quota,
                               this.readonly, this.recordsize, this.refer, this.refquota,
                               this.refreservation, this.reservation, this.used)

    entity
            .inherit_props
            .addAll(this.inherit_props?.map { InheritedProperty(0, it) }
                            ?: emptyList())

    return entity
}