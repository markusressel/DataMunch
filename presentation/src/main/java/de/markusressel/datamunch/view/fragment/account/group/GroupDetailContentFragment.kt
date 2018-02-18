package de.markusressel.datamunch.view.fragment.account.group

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.GroupPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.GroupEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_group_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class GroupDetailContentFragment : DetailContentFragmentBase<GroupEntity>() {

    @Inject
    protected lateinit var persistenceManager: GroupPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<GroupEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_group_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = "${entity.bsdgrp_gid}"

        groupTextView
                .text = entity
                .bsdgrp_group

        builtInCheckBox
                .isChecked = entity
                .bsdgrp_builtin

        sudoCheckBox
                .isChecked = entity
                .bsdgrp_sudo
    }

}