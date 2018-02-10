package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.system.update.UpdateModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class UpdateEntity(@Id var entityId: Long, val name: String, val operation: String)

fun UpdateModel.asEntity(): UpdateEntity {
    return UpdateEntity(0, this.name, this.operation)
}