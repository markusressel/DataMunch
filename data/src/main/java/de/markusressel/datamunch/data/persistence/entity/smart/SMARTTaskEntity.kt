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

package de.markusressel.datamunch.data.persistence.entity.smart

import de.markusressel.datamunch.data.IdentifiableListItem
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.library.tasks.smart.SMARTTaskModel
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class SMARTTaskEntity(@Id var entityId: Long = 0, val id: Long = 0,
                           val smarttest_dayweek: String = "", val smarttest_daymonth: String = "",
                           val smarttest_month: String = "", val smarttest_type: String = "",
                           val smarttest_hour: String = "", val smarttest_desc: String = "") :
    IdentifiableListItem, SearchableListItem {

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(smarttest_desc, smarttest_type)
    }

    @Backlink
    lateinit var smarttest_disks: ToMany<SMARTTestDisk>

}

fun SMARTTaskModel.asEntity(entityId: Long = 0): SMARTTaskEntity {
    val entity = SMARTTaskEntity(entityId, this.id, this.smarttest_dayweek, this.smarttest_daymonth,
                                 this.smarttest_month, this.smarttest_type, this.smarttest_hour,
                                 this.smarttest_desc)

    entity
            .smarttest_disks
            .addAll(this.smarttest_disks
                            .map {
                                SMARTTestDisk(0, it)
                            }.toList())

    return entity
}