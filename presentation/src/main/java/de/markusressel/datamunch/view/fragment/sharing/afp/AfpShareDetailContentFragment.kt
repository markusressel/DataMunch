package de.markusressel.datamunch.view.fragment.sharing.afp

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.AfpSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.AfpShareEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_sharing_afp_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class AfpShareDetailContentFragment : DetailContentFragmentBase<AfpShareEntity>() {

    @Inject
    protected lateinit var persistenceManager: AfpSharePersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<AfpShareEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_sharing_afp_detail


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
                .afp_name

    }

}