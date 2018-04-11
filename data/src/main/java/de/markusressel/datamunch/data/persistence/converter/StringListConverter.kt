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

package de.markusressel.datamunch.data.persistence.converter

import io.objectbox.converter.PropertyConverter

/**
 * Created by Markus on 09.02.2018.
 */
class StringListConverter : PropertyConverter<List<String>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<String> {
        if (databaseValue == null) return ArrayList()
        return databaseValue
                .split(",")
    }

    override fun convertToDatabaseValue(entityProperty: List<String>?): String {
        if (entityProperty == null) return ""
        if (entityProperty.isEmpty()) return ""
        val builder = StringBuilder()
        entityProperty
                .forEach {
                    builder
                            .append(it)
                            .append(",")
                }
        builder
                .deleteCharAt(builder.length - 1)
        return builder
                .toString()
    }
}