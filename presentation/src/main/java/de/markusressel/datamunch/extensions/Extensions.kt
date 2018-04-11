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

package de.markusressel.datamunch.extensions

import android.content.Context
import android.os.AsyncTask
import de.markusressel.datamunch.R
import java.util.*

/**
 * Created by Markus on 15.02.2018.
 */
fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

/**
 * Returns true if the current device is considered a tablet
 */
fun Context.isTablet(): Boolean {
    return resources
            .getBoolean(R.bool.is_tablet)
}

fun Any.doAsync(handler: () -> Unit) {
    object : AsyncTask<Void, Void, Void?>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            handler()
            return null
        }
    }.execute()
}

fun Throwable.prettyPrint(): String {
    val message = "${this.message}:\n" + "${this.stackTrace.joinToString(separator = "\n")}}"

    return message
}