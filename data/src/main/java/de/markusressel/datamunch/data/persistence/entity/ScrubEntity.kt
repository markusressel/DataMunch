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

import de.markusressel.datamunch.data.IdentifiableListItem
import de.markusressel.datamunch.data.SearchableListItem
import de.markusressel.freenasrestapiclient.library.storage.scrub.ScrubModel
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
                       val scrub_volume: String) : IdentifiableListItem, SearchableListItem {

    override fun getItemId(): Long = id

    override fun getSearchableContent(): List<Any> {
        return listOf(scrub_volume, scrub_description)
    }

}

fun ScrubModel.asEntity(entityId: Long = 0): ScrubEntity {
    return ScrubEntity(entityId, this.id, this.scrub_threshold, this.scrub_dayweek,
                       this.scrub_enabled, this.scrub_minute, this.scrub_hour, this.scrub_month,
                       this.scrub_daymonth, this.scrub_description, this.scrub_volume)
}