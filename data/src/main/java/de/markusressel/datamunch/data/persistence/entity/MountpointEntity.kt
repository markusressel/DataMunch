package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.model.MountpointModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class MountpointEntity(@Id var entityId: Long, val id: Long, val destination: String,
                            val jail: String, val mounted: Boolean, val readonly: Boolean,
                            val source: String)

fun MountpointModel.asEntity(): MountpointEntity {
    return MountpointEntity(0, this.id, this.destination, this.jail, this.mounted, this.readonly, this.source)
}