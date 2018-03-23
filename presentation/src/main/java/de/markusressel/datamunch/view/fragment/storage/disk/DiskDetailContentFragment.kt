package de.markusressel.datamunch.view.fragment.storage.disk

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.DiskPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.DiskEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_disk_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class DiskDetailContentFragment : DetailContentFragmentBase<DiskEntity>() {

    @Inject
    protected lateinit var persistenceManager: DiskPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<DiskEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_disk_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = entity
                .disk_identifier

        nameTextView
                .text = entity
                .disk_name

        parentTypeTextView
                .text = entity
                .disk_description

        fullNameTextView
                .text = entity
                .disk_acousticlevel

        filesystemTextView
                .text = entity
                .disk_advpowermgmt

        filesystemTextView
                .text = entity
                .disk_hddstandby

        filesystemTextView
                .text = entity
                .disk_multipath_member

        filesystemTextView
                .text = entity
                .disk_multipath_name

        filesystemTextView
                .text = entity
                .disk_serial

        filesystemTextView
                .text = entity
                .disk_smartoptions

        filesystemTextView
                .text = "${entity.disk_togglesmart}"

        filesystemTextView
                .text = entity
                .disk_transfermode

        filesystemTextView
                .text = "${entity.disk_expiretime}"

        filesystemTextView
                .text = "${entity.disk_size}"

    }

}