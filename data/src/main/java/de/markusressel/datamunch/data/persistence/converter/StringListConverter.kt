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