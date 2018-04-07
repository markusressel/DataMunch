package de.markusressel.datamunch.view.component

import android.content.Context
import de.markusressel.datamunch.view.fragment.base.LifecycleFragmentBase

abstract class FragmentComponent(private val hostFragment: LifecycleFragmentBase) {

    protected val fragment
        get() = hostFragment

    val context: Context? = hostFragment.context

}