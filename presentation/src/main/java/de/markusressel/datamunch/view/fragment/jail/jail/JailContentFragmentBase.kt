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