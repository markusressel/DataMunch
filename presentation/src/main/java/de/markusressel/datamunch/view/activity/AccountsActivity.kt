package de.markusressel.datamunch.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import de.markusressel.datamunch.R
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.base.BottomNavigationActivity
import de.markusressel.datamunch.view.fragment.GroupsFragment
import de.markusressel.datamunch.view.fragment.UsersFragment


class AccountsActivity : BottomNavigationActivity() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_accounts

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder
                .Accounts
                .identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        setTitle(R.string.menu_item_accounts)

        onBottomNavigationItemSelected(0, false)
    }

    override fun getBottomNavigationItems(): List<AHBottomNavigationItem> {
        return listOf(AHBottomNavigationItem(getString(R.string.users), R.drawable.cube_outline),
                      AHBottomNavigationItem(getString(R.string.groups),
                                             iconHandler.getBottomNavigationIcon(
                                                     MaterialDesignIconic.Icon.gmi_power_input),
                                             ContextCompat.getColor(this, R.color.md_red_A400)))
    }

    override fun onBottomNavigationItemSelected(position: Int, wasSelected: Boolean) {
        val contentFragment = when (position) {
            1 -> GroupsFragment()
            else -> UsersFragment()
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, contentFragment)
                //                .addToBackStack(preferencesFragment.tag)
                .commit()
    }

}