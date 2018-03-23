package de.markusressel.datamunch.view.fragment.pages

import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.sharing.afp.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.cifs.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.nfs.NfsSharesFragment


class SharingPage : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() {
            return listOf(R.string.afp to ::AfpSharesFragment, R.string.nfs to ::NfsSharesFragment,
                          R.string.cifs to ::CifsSharesFragment)
        }

}