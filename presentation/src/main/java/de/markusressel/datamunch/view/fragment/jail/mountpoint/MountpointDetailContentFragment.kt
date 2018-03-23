package de.markusressel.datamunch.view.fragment.jail.mountpoint

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.MountpointPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.MountpointEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_jails_mountpoint_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class MountpointDetailContentFragment : DetailContentFragmentBase<MountpointEntity>() {

    @Inject
    protected lateinit var persistenceManager: MountpointPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<MountpointEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jails_mountpoint_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.id}"

        sourceTextView
                .text = entity
                .source

        destinationTextView
                .text = entity
                .destination
    }

}