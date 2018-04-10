package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 09.04.2018.
 */
@Entity
data class SortOptionEntity(@Id var entityId: Long, val id: Long, val type: Long,
                            val reversed: Boolean)