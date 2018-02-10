package de.markusressel.datamunch.data.persistence.entity

import de.markusressel.freenaswebapiclient.sharing.cifs.CifsShareModel
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by Markus on 30.01.2018.
 */
@Entity
data class CifsShareEntity(@Id var entityId: Long, val id: Long, val cifs_hostsallow: String,
                           val cifs_name: String, val cifs_home: Boolean,
                           val cifs_default_permissions: Boolean, val cifs_guestok: Boolean,
                           val cifs_showhiddenfiles: Boolean, val cifs_hostsdeny: String,
                           val cifs_recyclebin: Boolean, val cifs_auxsmbconf: String,
                           val cifs_comment: String, val cifs_path: String, val cifs_ro: Boolean,
                           val cifs_guestonly: Boolean, val cifs_browsable: Boolean)

fun CifsShareModel.asEntity(): CifsShareEntity {
    return CifsShareEntity(0, this.id, this.cifs_hostsallow, this.cifs_name, this.cifs_home,
                           this.cifs_default_permissions, this.cifs_guestok,
                           this.cifs_showhiddenfiles, this.cifs_hostsdeny, this.cifs_recyclebin,
                           this.cifs_auxsmbconf, this.cifs_comment, this.cifs_path, this.cifs_ro,
                           this.cifs_guestonly, this.cifs_browsable)
}