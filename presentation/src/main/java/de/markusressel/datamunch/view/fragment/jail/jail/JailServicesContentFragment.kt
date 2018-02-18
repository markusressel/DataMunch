package de.markusressel.datamunch.view.fragment.jail.jail

import android.os.Bundle
import android.view.View
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 18.02.2018.
 */
class JailServicesContentFragment : DetailContentFragmentBase<JailEntity>() {

    @Inject
    lateinit var persistenceManager: JailPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_jail_services

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

    }

}