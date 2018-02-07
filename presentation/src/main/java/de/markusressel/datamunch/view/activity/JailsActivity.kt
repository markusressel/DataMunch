package de.markusressel.datamunch.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.BottomNavigationActivity
import de.markusressel.datamunch.view.fragment.JailsFragment
import de.markusressel.datamunch.view.fragment.MountpointsFragment
import de.markusressel.datamunch.view.fragment.TemplatesFragment


class JailsActivity : BottomNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_jails

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Jails
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_jails)

        onBottomNavigationItemSelected(0, false)
    }

    override fun getBottomNavigationItems(): List<AHBottomNavigationItem> {
        return listOf(AHBottomNavigationItem(getString(R.string.jails), R.drawable.cube_outline),
                      AHBottomNavigationItem(getString(R.string.mountpoints),
                                             iconHandler.getBottomNavigationIcon(
                                                     MaterialDesignIconic.Icon.gmi_power_input),
                                             ContextCompat.getColor(this, R.color.md_red_A400)),
                      AHBottomNavigationItem(getString(R.string.templates),
                                             iconHandler.getBottomNavigationIcon(
                                                     MaterialDesignIconic.Icon.gmi_file),
                                             ContextCompat.getColor(this, R.color.md_blue_A400)))
    }

    override fun onBottomNavigationItemSelected(position: Int, wasSelected: Boolean) {
        val contentFragment = when (position) {
            1 -> MountpointsFragment()
            2 -> TemplatesFragment()
            else -> JailsFragment()
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, contentFragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}