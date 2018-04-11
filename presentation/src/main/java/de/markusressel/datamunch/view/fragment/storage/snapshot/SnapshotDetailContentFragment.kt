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

package de.markusressel.datamunch.view.fragment.storage.snapshot

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.SnapshotPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.SnapshotEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_snapshot_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class SnapshotDetailContentFragment : DetailContentFragmentBase<SnapshotEntity>() {

    @Inject
    protected lateinit var persistenceManager: SnapshotPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<SnapshotEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_storage_snapshot_detail


    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        idTextView
                .text = entity
                .id

        nameTextView
                .text = entity
                .name

        fullNameTextView
                .text = entity
                .fullname

        filesystemTextView
                .text = entity
                .filesystem

        parentTypeTextView
                .text = entity
                .parent_type

        referTextView
                .text = entity
                .refer

        usedTextView
                .text = entity
                .used

    }

}