/*
 * Copyright (c) 2017 Markus Ressel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.markusressel.datamunch.view.preferences

import android.content.Context
import android.support.v7.preference.ListPreference
import android.util.AttributeSet

/**
 * Created by Markus on 31.07.2016.
 */
class IntListPreference : ListPreference {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    override fun persistString(value: String?): Boolean {
        return if (value == null) {
            false
        } else {
            persistInt(Integer.valueOf(value)!!)
        }
    }

    override fun getPersistedString(defaultReturnValue: String?): String {
        if (sharedPreferences.contains(key)) {
            val intValue = getPersistedInt(0)
            return intValue.toString()
        } else {
            return defaultReturnValue as String
        }
    }

}
