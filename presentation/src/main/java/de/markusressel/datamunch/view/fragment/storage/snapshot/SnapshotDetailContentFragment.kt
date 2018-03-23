package de.markusressel.datamunch.view.fragment.storage.snapshot

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SnapshotPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_snapshot_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class SnapshotDetailContentFragment : DetailContentFragmentBase<SnapshotEntity>() {

    @Inject
    protected lateinit var persistenceManager: SnapshotPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SnapshotEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_snapshot_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = entity
                .id

        nameTextView
                .text = entity
                .name

        fullNameTextView
                .text = entity
                .fullname

        filesystemTextView
                .text = entity
                .filesystem

        parentTypeTextView
                .text = entity
                .parent_type

        referTextView
                .text = entity
                .refer

        usedTextView
                .text = entity
                .used

    }

}