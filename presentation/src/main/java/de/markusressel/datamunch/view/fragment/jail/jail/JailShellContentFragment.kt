package de.markusressel.datamunch.view.fragment.jail.jail

import android.os.Bundle
import android.view.View
import de.markusressel.datamunch.R

/**
 * Created by Markus on 18.02.2018.
 */
class JailShellContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jail_shell

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

    }

}