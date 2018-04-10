package de.markusressel.datamunch.view.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.view.IconHandler
import javax.inject.Inject

abstract class DaggerBottomSheetFragmentBase : BottomSheetDialogFragment(),
    HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {
        AndroidSupportInjection
                .inject(this)
        super
                .onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    @Inject
    protected lateinit var preferenceHandler: PreferenceHandler

    @Inject
    protected lateinit var iconHandler: IconHandler

    /**
     * The layout resource for this Activity
     */
    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val newContainer = inflater.inflate(layoutRes, container, false) as ViewGroup

        val alternative = super
                .onCreateView(inflater, newContainer, savedInstanceState)

        return alternative
                ?: newContainer
    }

}