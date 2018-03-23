package de.markusressel.datamunch.view.fragment.sharing.cifs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.CifsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.CifsShareEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_disk_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class CifsShareDetailContentFragment : DetailContentFragmentBase<CifsShareEntity>() {

    @Inject
    protected lateinit var persistenceManager: CifsSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<CifsShareEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_sharing_cifs_detail


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
                .cifs_name

    }

}