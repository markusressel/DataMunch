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

import de.markusressel.datamunch.data.persistence.PluginPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.EntityTypeId
import de.markusressel.datamunch.data.persistence.entity.PluginEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.ListFragmentBase
import de.markusressel.datamunch.view.fragment.base.SortOption
import de.markusressel.freenasrestapiclient.library.plugins.PluginModel
import io.reactivex.Single
import javax.inject.Inject


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class PluginsFragment : ListFragmentBase<PluginModel, PluginEntity>() {

    @Inject
    lateinit var persistenceManager: PluginPersistenceManager

    override val entityTypeId: Long
        get() = EntityTypeId.Plugin.id

    override fun getPersistenceHandler(): PersistenceManagerBase<PluginEntity> = persistenceManager


    override fun loadListDataFromSource(): Single<List<PluginModel>> {
        return freeNasWebApiClient
                .getPlugins()
    }

    override fun mapToEntity(it: PluginModel): PluginEntity {
        return it
                .asEntity()
    }

    override fun getAllSortCriteria(): List<SortOption<PluginEntity>> {
        return listOf(SortOption.PLUGIN_NAME)
    }

    private fun openDetailView(plugin: PluginEntity) {
        context
                ?.let {
                    val intent = DetailActivityBase
                            .newInstanceIntent(PluginDetailActivity::class.java, it,
                                               plugin.entityId)
                    startActivity(intent)
                }
    }

}
