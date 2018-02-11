package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.sharing.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.sharing.NfsSharesFragment
import kotlin.reflect.KFunction0


class SharingActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Sharing
    }

    override val tabItems: List<Pair<Int, KFunction0<DaggerSupportFragmentBase>>>
        get() {
            return listOf(R.string.afp to ::AfpSharesFragment, R.string.nfs to ::NfsSharesFragment,
                          R.string.cifs to ::CifsSharesFragment)
        }

}