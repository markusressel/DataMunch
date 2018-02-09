package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.storage.volume.VolumeModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class VolumeEntity(@Id var entityId: Long, val id: Long, val status: String,
                        val vol_guid: Long, val used: String, val name: String,
                        val used_pct: String, val used_si: String?, val vol_encryptkey: String?,
                        val vol_name: String?, val decrypted: Boolean, val avail_si: String?,
                        val mountpoint: String, val vol_encrypt: Long, val total_si: String?) {

    //    lateinit var parent: ToOne<VolumeEntity>
    //
    //    @Backlink
    //    lateinit var children: ToMany<VolumeEntity>

    var childEntities: List<VolumeEntity> = emptyList()

}

fun VolumeModel.asEntity(): VolumeEntity {
    val childEntities: List<VolumeEntity> = createEntityReq(listOf(this))
    return childEntities
            .first()
}


private fun createEntityReq(children: List<VolumeModel>?): List<VolumeEntity> {

    fun convertToEntity(model: VolumeModel): VolumeEntity {
        return VolumeEntity(0, model.id, model.status, model.vol_guid, model.used, model.name,
                            model.used_pct, model.used_si, model.vol_encryptkey, model.vol_name,
                            model.is_decrypted, model.avail_si, model.mountpoint, model.vol_encrypt,
                            model.total_si)
    }

    if (children == null) {
        return emptyList()
    }

    val entities: MutableList<VolumeEntity> = mutableListOf()

    for (child in children) {
        val entity = convertToEntity(child)
        entity
                .childEntities = createEntityReq(child.children)

        entities
                .add(entity)
    }

    return entities
}
