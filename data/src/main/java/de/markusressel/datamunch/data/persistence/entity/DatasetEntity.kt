package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenasrestapiclient.library.storage.dataset.DatasetModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */

@Entity
data class DatasetEntity(@Id var entityId: Long, val atime: String, val avail: Long,
                         val comments: String?, val compression: String, val dedup: String,
        //                         @Convert(converter = StringListConverter::class,
        //                                                     dbType = String::class) val inherit_props: List<String>?,
                         val mountpoint: String, val name: String, val pool: String,
                         val quota: Long, val readonly: String, val recordsize: Long,
                         val refer: Long, val refquota: Long, val refreservation: Long,
                         val reservation: Long, val used: Long)

fun DatasetModel.asEntity(): DatasetEntity {
    return DatasetEntity(0, this.atime, this.avail, this.comments, this.compression, this.dedup,
            //                         this.inherit_props,
                         this.mountpoint, this.name, this.pool, this.quota, this.readonly,
                         this.recordsize, this.refer, this.refquota, this.refreservation,
                         this.reservation, this.used)
}