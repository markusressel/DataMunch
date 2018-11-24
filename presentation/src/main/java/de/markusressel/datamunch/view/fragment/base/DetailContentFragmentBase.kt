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

package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.view.View
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import kotlinx.android.synthetic.main.content_accounts_user_detail.*

/**
 * Created by Markus on 15.02.2018.
 */
abstract class DetailContentFragmentBase<EntityType : Any> : DaggerSupportFragmentBase() {

    val entityId: Long by lazy {
        val args = arguments
                ?: throw IllegalStateException("Arguments must not be null!")

        if (args.containsKey(DetailActivityBase.KEY_ENTITY_ID)) {
            args.getLong(DetailActivityBase.KEY_ENTITY_ID)
        } else {
            throw IllegalStateException("No entity id specified!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        MaterialViewPagerHelper
                .registerScrollView(activity, scrollView)
    }

    /**
     * Get the persistence handler for this view
     */
    protected abstract fun getPersistenceHandler(): PersistenceManagerBase<EntityType>

    /**
     * Get the entity to edit from persistence
     */
    protected fun getEntityFromPersistence(): EntityType {
        return getPersistenceHandler()
                .standardOperation()
                .get(entityId)
    }

    /**
     * Store a modified version of the entity in persistence
     */
    protected fun storeModifiedEntity(modifiedEntity: EntityType) {
        getPersistenceHandler()
                .standardOperation()
                .put(modifiedEntity)
    }

}