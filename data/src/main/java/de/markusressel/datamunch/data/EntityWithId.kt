/*
 * Copyright (C) 2018 Markus Ressel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.data

/**
 * Interface for generic access to the id of an entity
 */
interface EntityWithId {

    /**
     * This id is the database entity id
     */
    fun getDbEntityId(): Long

    /**
     * Sets the database entity id
     */
    fun setDbEntityId(id: Long)

    /**
     * This id (and the class of an item) is used to check if two
     * elements in two lists are essentially the same item (altough not the same object).
     * This is used in @see ListFragmentBase when the contents of the list are updated.
     */
    fun getItemId(): Long

}