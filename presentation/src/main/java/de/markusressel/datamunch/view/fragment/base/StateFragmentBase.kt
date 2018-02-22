package de.markusressel.datamunch.view.fragment.base

import android.os.Bundle
import com.pascalwelsch.compositeandroid.fragment.CompositeFragment
import de.markusressel.datamunch.view.InstanceStateProvider

/**
 * Created by Markus on 21.02.2018.
 */
abstract class StateFragmentBase : CompositeFragment() {

    private val stateBundle = Bundle()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            stateBundle
                    .putAll(savedInstanceState.getBundle(KEY_BUNDLE))
        }

        super
                .onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState
                .putBundle(KEY_BUNDLE, stateBundle)

        super
                .onSaveInstanceState(outState)
    }

    /**
     * Bind a nullable property
     */
    protected fun <T> savedInstanceState() = InstanceStateProvider.Nullable<T>(stateBundle)

    /**
     * Bind a non-null property
     */
    protected fun <T> savedInstanceState(defaultValue: T) = InstanceStateProvider.NotNull(
            stateBundle, defaultValue)

    companion object {
        const val KEY_BUNDLE = "saved_state"
    }

}