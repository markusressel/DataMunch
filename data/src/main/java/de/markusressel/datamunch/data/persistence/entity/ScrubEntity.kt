package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.storage.scrub.ScrubModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class ScrubEntity(@Id var entityId: Long, val id: Long, val scrub_threshold: Int,
                       val scrub_dayweek: String, val scrub_enabled: Boolean,
                       val scrub_minute: String, val scrub_hour: String, val scrub_month: String,
                       val scrub_daymonth: String, val scrub_description: String,
                       val scrub_volume: String)

fun ScrubModel.asEntity(entityId: Long = 0): ScrubEntity {
    return ScrubEntity(entityId, this.id, this.scrub_threshold, this.scrub_dayweek,
                       this.scrub_enabled, this.scrub_minute, this.scrub_hour, this.scrub_month,
                       this.scrub_daymonth, this.scrub_description, this.scrub_volume)
}