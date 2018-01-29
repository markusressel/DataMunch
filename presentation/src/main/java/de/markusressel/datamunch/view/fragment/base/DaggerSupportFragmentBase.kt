package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import javax.inject.Inject

/**
 * Base class for implementing a fragment
 *
 * Created by Markus on 07.01.2018.
 */
abstract class DaggerSupportFragmentBase : DaggerFragment() {

    protected lateinit var rootView: View

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    /**
     * The layout resource for this Activity
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(layoutRes, container, false)

        return rootView
    }

}