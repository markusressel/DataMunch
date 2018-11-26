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

package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.base.TabPageConstructor
import de.markusressel.datamunch.view.fragment.system.MaintenanceFragment
import de.markusressel.datamunch.view.fragment.system.alert.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.update.UpdatesFragment


class SystemPage : TabNavigationFragment() {

    override val tabItems: List<TabPageConstructor>
        get() {
            return listOf(R.string.alerts to ::AlertsFragment,
                          R.string.updates to ::UpdatesFragment,
                          R.string.maintenance to ::MaintenanceFragment)
        }

}