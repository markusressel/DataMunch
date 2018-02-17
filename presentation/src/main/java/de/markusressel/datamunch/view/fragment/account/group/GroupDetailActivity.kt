package de.markusressel.datamunch.view.fragment.account.group

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class GroupDetailActivity : DetailActivityBase<GroupEntity>() {

    @Inject
    lateinit var persistenceHandler: GroupPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<GroupEntity> = persistenceHandler

    override val headerTextString: String
        get() = getEntity().bsdgrp_group

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() = listOf(R.string.details to ::GroupDetailContentFragment)


}