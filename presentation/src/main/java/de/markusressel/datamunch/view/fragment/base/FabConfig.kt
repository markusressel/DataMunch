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

import android.support.annotation.ColorRes
import com.mikepenz.iconics.typeface.IIcon

/**
 * Created by Markus on 13.02.2018.
 */
data class FabConfig(val left: MutableList<Fab>, val right: MutableList<Fab>) {
    data class Fab(val description: String, val icon: IIcon, @ColorRes val color: Int? = null,
                   val onClick: (() -> Unit)? = null, val onLongClick: (() -> Boolean)? = null)
}