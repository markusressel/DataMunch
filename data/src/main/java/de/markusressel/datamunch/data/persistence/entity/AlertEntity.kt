package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.system.alert.AlertModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class AlertEntity(@Id var entityId: Long, val id: String, val level: String,
                       val message: String, val dismissed: Boolean)

fun AlertModel.asEntity(): AlertEntity {
    return AlertEntity(0, this.id, this.level, this.message, this.dismissed)
}