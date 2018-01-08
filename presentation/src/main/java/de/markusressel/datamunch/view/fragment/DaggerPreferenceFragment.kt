package de.markusressel.datamunch.view.fragment

import android.app.Fragment
import android.content.Context
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.mrapp.android.preference.activity.PreferenceFragment
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */

abstract class DaggerPreferenceFragment : PreferenceFragment(), HasFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var preferenceHandler: PreferenceHandler

    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)

        super.onAttach(context)
    }

    override fun fragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }

}
