package de.markusressel.datamunch.view.activity

import android.os.Bundle
import android.preference.Preference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mrapp.android.preference.activity.PreferenceFragment
import de.mrapp.android.preference.activity.RestoreDefaultsListener
import de.mrapp.android.preference.activity.fragment.AbstractPreferenceFragment

/**
 * Created by Markus on 06.01.2018.
 */
class FileUploaderPreferences : AbstractPreferenceFragment(), RestoreDefaultsListener {

    override fun onInflateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoredDefaultValue(fragment: PreferenceFragment, preference: Preference, oldValue: Any?, newValue: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValueRequested(fragment: PreferenceFragment, preference: Preference, currentValue: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreDefaultValuesRequested(fragment: PreferenceFragment): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}