package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.pages.JailFragment
import kotlin.reflect.KFunction0


class JailActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .Jails
    }

    override val contentFragment: KFunction0<DaggerSupportFragmentBase>
        get() = ::JailFragment


}