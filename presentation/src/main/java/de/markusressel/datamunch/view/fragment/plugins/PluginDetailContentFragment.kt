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

package de.markusressel.datamunch.view.fragment.plugins

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.view.fragment.base.DetailContentFragmentBase
import kotlinx.android.synthetic.main.content_storage_disk_detail.*
import javax.inject.Inject

/**
 * Created by Markus on 15.02.2018.
 */
class PluginDetailContentFragment : DetailContentFragmentBase<PluginEntity>() {

    @Inject
    protected lateinit var persistenceManager: PluginPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> = persistenceManager

    override val layoutRes: Int
        get() = R.layout.content_plugin_plugin_detail


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
                .plugin_name
    }

}