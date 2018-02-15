package de.markusressel.datamunch.view.activity.fileuploader

import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.navigation.DrawerMenuItem
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.pages.FileUploaderFragment

class FileUploaderActivity : NavigationDrawerActivity() {
    override val style: Int
        get() = DEFAULT

    override fun getDrawerMenuItem(): DrawerMenuItem {
        return DrawerItemHolder
                .FileUploader
    }

    override val contentFragment: () -> DaggerSupportFragmentBase
        get() = ::FileUploaderFragment

}
