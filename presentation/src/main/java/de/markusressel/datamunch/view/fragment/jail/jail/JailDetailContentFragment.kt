package de.markusressel.datamunch.view.fragment.jail.jail

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_jail_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class JailDetailContentFragment : DetailContentFragmentBase<JailEntity>() {

    @Inject
    lateinit var persistenceManager: JailPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jail_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()
                ?: return

        nameTextView
                .text = entity
                .jail_host

    }

}