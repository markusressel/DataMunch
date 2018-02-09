package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.storage.volume.VolumeModel
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class VolumeEntity(@Id var entityId: Long, val id: Long, val status: String,
                        val vol_guid: Long, val used: String, val name: String,
                        val used_pct: String, val used_si: String?, val vol_encryptkey: String?,
                        val vol_name: String?, val decrypted: Boolean, val avail_si: String?,
                        val mountpoint: String, val vol_encrypt: Long, val total_si: String?)

@Backlink
lateinit var children: ToMany<ChildVolumeEntity>

fun VolumeModel.asEntity(): Pair<VolumeEntity, List<ChildVolumeEntity>> {
    val volume: VolumeEntity = VolumeEntity(0, this.id, this.status, this.vol_guid, this.used,
                                            this.name, this.used_pct, this.used_si,
                                            this.vol_encryptkey, this.vol_name, this.is_decrypted,
                                            this.avail_si, this.mountpoint, this.vol_encrypt,
                                            this.total_si)

    val childVolumes: List<ChildVolumeEntity> = this.children?.map {
        ChildVolumeEntity(0, it.id, it.status, it.vol_guid, it.used, it.name, it.used_pct,
                          it.used_si, it.vol_encryptkey, it.vol_name, it.is_decrypted, it.avail_si,
                          it.mountpoint, it.vol_encrypt, it.total_si)
    }
            ?: emptyList()

    return Pair(volume, childVolumes)
}