package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.jails.template.TemplateModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class TemplateEntity(@Id var entityId: Long, val id: Long, val jt_arch: String,
                          val jt_instances: Long, val jt_name: String, val jt_os: String,
                          val jt_url: String)

fun TemplateModel.asEntity(): TemplateEntity {
    return TemplateEntity(0, this.id, this.jt_arch, this.jt_instances, this.jt_name, this.jt_os,
                          this.jt_url)
}