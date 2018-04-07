package de.markusressel.datamunch.view.fragment.sharing.nfs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.NfsShareEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_sharing_nfs_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class NfsShareDetailContentFragment : DetailContentFragmentBase<NfsShareEntity>() {

    @Inject
    protected lateinit var persistenceManager: NfsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<NfsShareEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_sharing_nfs_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.id}"

        nameTextView
                .text = entity
                .nfs_network

    }

}