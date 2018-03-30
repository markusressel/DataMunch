package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenasrestapiclient.library.tasks.smart.SMARTTaskModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class SMARTTaskEntity(@Id var entityId: Long, val id: Long, val smarttest_dayweek: String,
                           val smarttest_daymonth: String,
        //                           val smarttest_disks: Array<String>,
                           val smarttest_month: String, val smarttest_type: String,
                           val smarttest_hour: String, val smarttest_desc: String)

fun SMARTTaskModel.asEntity(entityId: Long = 0): SMARTTaskEntity {
    return SMARTTaskEntity(entityId, this.id, this.smarttest_dayweek, this.smarttest_daymonth,
            //                           this.smarttest_disks,
                           this.smarttest_month, this.smarttest_type, this.smarttest_hour,
                           this.smarttest_desc)
}