package de.markusressel.datamunch.view.fragment.jail.jail

import de.markusressel.datamunch.data.persistence.JailPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import javax.inject.Inject

/**
 * Created by Markus on 18.02.2018.
 */
abstract class JailContentFragmentBase : DetailContentFragmentBase<JailEntity>() {

    @Inject
    lateinit var persistenceManager: JailPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<JailEntity> = persistenceManager

}