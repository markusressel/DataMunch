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