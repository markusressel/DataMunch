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
import de.markusressel.freenasrestapiclient.api.v1.storage.disk.DiskModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class DiskEntity(@Id var entityId: Long, val disk_acousticlevel: String,
                      val disk_advpowermgmt: String, val disk_serial: String, val disk_size: Long,
                      val disk_multipath_name: String, val disk_identifier: String,
                      val disk_togglesmart: Boolean, val disk_hddstandby: String,
                      val disk_transfermode: String, val disk_multipath_member: String,
                      val disk_description: String, val disk_smartoptions: String,
                      val disk_expiretime: Long?, val disk_name: String) : EntityWithId {

    override fun getDbEntityId(): Long = entityId
    override fun setDbEntityId(id: Long) {
        entityId = id
    }

    override fun getItemId(): Long = entityId

}

fun DiskModel.asEntity(): DiskEntity {
    return DiskEntity(0, this.disk_acousticlevel, this.disk_advpowermgmt, this.disk_serial,
            this.disk_size, this.disk_multipath_name, this.disk_identifier,
            this.disk_togglesmart, this.disk_hddstandby, this.disk_transfermode,
            this.disk_multipath_member, this.disk_description, this.disk_smartoptions,
            this.disk_expiretime, this.disk_name)
}