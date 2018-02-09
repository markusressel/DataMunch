package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.storage.disk.DiskModel
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
                      val disk_expiretime: Long?, val disk_name: String)

fun DiskModel.asEntity(): DiskEntity {
    return DiskEntity(0, this.disk_acousticlevel, this.disk_advpowermgmt, this.disk_serial,
                      this.disk_size, this.disk_multipath_name, this.disk_identifier,
                      this.disk_togglesmart, this.disk_hddstandby, this.disk_transfermode,
                      this.disk_multipath_member, this.disk_description, this.disk_smartoptions,
                      this.disk_expiretime, this.disk_name)
}