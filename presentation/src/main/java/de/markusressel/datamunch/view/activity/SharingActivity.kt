package de.markusressel.datamunch.view.activity

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.TabNavigationActivity
import de.markusressel.datamunch.view.fragment.AfpSharesFragment
import de.markusressel.datamunch.view.fragment.CifsSharesFragment
import de.markusressel.datamunch.view.fragment.NfsSharesFragment


class SharingActivity : TabNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Sharing
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_sharing)
    }

    override fun getTabItems(): List<TabItemConfig> {
        return listOf(TabItemConfig(R.string.afp, ::AfpSharesFragment),
                      TabItemConfig(R.string.nfs, ::NfsSharesFragment),
                      TabItemConfig(R.string.cifs, ::CifsSharesFragment))
    }

    override fun onTabItemSelected(position: Int, wasSelected: Boolean) {
    }

}