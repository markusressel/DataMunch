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

package de.markusressel.datamunch.view.fragment.account.user

import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.UserPersistenceManager
import de.markusressel.datamunch.data.persistence.base.PersistenceManagerBase
import de.markusressel.datamunch.data.persistence.entity.UserEntity
import de.markusressel.datamunch.view.activity.base.DetailActivityBase
import de.markusressel.datamunch.view.fragment.base.TabPageConstructor
import javax.inject.Inject


/**
 * Created by Markus on 15.02.2018.
 */
class UserDetailActivity : DetailActivityBase<UserEntity>() {

    @Inject
    lateinit var persistenceHandler: UserPersistenceManager

    override fun getPersistenceHandler(): PersistenceManagerBase<UserEntity> = persistenceHandler

    override val headerTextString: String
        get() {
            val entity = getEntity()
            if (!entity.bsdusr_builtin && entity.bsdusr_full_name.isNotEmpty()) {
                return entity
                        .bsdusr_full_name
            }

            return "${getString(R.string.user)}: ${entity.bsdusr_username}"
        }

    override val tabItems: List<TabPageConstructor>
        get() = listOf(R.string.details to ::UserDetailContentFragment)


}