/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.view.fragment.sharing.nfs

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.NfsSharePersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.nfs.NfsShareEntity
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

        hostsTextView
                .text = entity
                .nfs_hosts
        commentTextView
                .text = entity
                .nfs_comment
        mapAllGroupTextView
                .text = entity
                .nfs_mapall_group
        mapAllUserTextView
                .text = entity
                .nfs_mapall_user
        mapRootGroupTextView
                .text = entity
                .nfs_maproot_group
        mapRootUserTextView
                .text = entity
                .nfs_maproot_user
        quietCheckbox
                .isChecked = entity
                .nfs_quiet
        roCheckbox
                .isChecked = entity
                .nfs_ro
        securityTextView
                .text = entity
                .nfs_security
                .joinToString { "\n" }
        pathsTextView
                .text = entity
                .nfs_paths
                .joinToString { "\n" }

    }

}