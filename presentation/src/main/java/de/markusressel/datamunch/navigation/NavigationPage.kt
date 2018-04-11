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

package de.markusressel.datamunch.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Created by Markus on 08.01.2018.
 */
class NavigationPage(val activityClass: Class<*>? = null, val fragment: (() -> Fragment)? = null,
                     val tag: String? = null) {

    fun createIntent(context: Context): Intent {
        return Intent(context, activityClass)
    }

}