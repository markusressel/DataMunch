package de.markusressel.datamunch.view.fragment.storage.scrubs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.ScrubPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.ScrubEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_scrub_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class ScrubDetailContentFragment : DetailContentFragmentBase<ScrubEntity>() {

    @Inject
    protected lateinit var persistenceManager: ScrubPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<ScrubEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_scrub_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = entity
                .scrub_description

    }

}