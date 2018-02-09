package de.markusressel.datamunch.data.persistence.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne


/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class ChildVolumeEntity(@Id var entityId: Long, val id: Long, val status: String,
                             val vol_guid: Long, val used: String, val name: String,
                             val used_pct: String, val used_si: String?,
                             val vol_encryptkey: String?, val vol_name: String?,
                             val decrypted: Boolean, val avail_si: String?, val mountpoint: String,
                             val vol_encrypt: Long, val total_si: String?)

lateinit var parent: ToOne<VolumeEntity>

fun ChildVolumeEntity.asEntity(): VolumeEntity {
    return VolumeEntity(0, this.id, this.status, this.vol_guid, this.used, this.name, this.used_pct,
                        this.used_si, this.vol_encryptkey, this.vol_name, this.decrypted,
                        this.avail_si, this.mountpoint, this.vol_encrypt, this.total_si)
}