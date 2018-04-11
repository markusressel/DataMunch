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

package de.markusressel.datamunch.view.fragment.preferences

import android.preference.Preference
import de.markusressel.datamunch.R
import de.mrapp.android.preference.activity.PreferenceFragment
import de.mrapp.android.preference.activity.RestoreDefaultsListener

/**
 * Created by Markus on 06.01.2018.
 */
class FileUploaderPreferences : DaggerPreferenceFragment(), RestoreDefaultsListener {

    override fun getPreferencesResource(): Int {
        return R
                .xml
                .preferences_file_uploader
    }

    override fun findPreferences() {
    }

    override fun updateSummaries() {
    }

    override fun onRestoredDefaultValue(fragment: PreferenceFragment, preference: Preference,
                                        oldValue: Any?, newValue: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValueRequested(fragment: PreferenceFragment,
                                                preference: Preference,
                                                currentValue: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValuesRequested(fragment: PreferenceFragment): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}