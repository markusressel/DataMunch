package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.pages.StorageFragment
import kotlin.reflect.KFunction0


class StorageActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Storage
    }

    override val contentFragment: KFunction0<DaggerSupportFragmentBase>
        get() = ::StorageFragment


}