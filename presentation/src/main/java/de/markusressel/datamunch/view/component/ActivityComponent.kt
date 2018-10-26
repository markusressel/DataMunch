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

package de.markusressel.datamunch.view.component

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

abstract class ActivityComponent(private val hostActivity: () -> LifecycleProvider<ActivityEvent>) {

    init {
        // check if both casts are successful
        activity
        lifecycleOwner
        lifecycleProvider
    }

    protected val activity: AppCompatActivity
        get() = hostActivity() as AppCompatActivity

    protected val lifecycleOwner: LifecycleOwner
        get() = hostActivity() as LifecycleOwner

    protected val lifecycleProvider: LifecycleProvider<ActivityEvent>
        get() = hostActivity()

}