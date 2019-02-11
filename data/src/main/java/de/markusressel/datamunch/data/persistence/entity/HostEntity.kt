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

import de.markusressel.datamunch.data.EntityWithId
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 31.01.2018.
 */
@Entity
data class HostEntity(@Id var id: Long, val hostname: String) : EntityWithId {

    override fun getDbEntityId(): Long = id
    override fun setDbEntityId(id: Long) {
        this.id = id
    }

    override fun getItemId(): Long = id

}