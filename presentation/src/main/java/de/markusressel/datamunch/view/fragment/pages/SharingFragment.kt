package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.sharing.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.NfsSharesFragment
import kotlin.reflect.KFunction0


class SharingFragment : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.afp to ::AfpSharesFragment, R.string.nfs to ::NfsSharesFragment,
                          R.string.cifs to ::CifsSharesFragment)
        }

}