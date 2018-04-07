package de.markusressel.datamunch.view.activity

import android.view.View
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.component.LockComponent

class MainActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    private val lockComponent: LockComponent = LockComponent(this, { preferenceHandler })

    override fun setContentView(view: View?) {
        val contentView = lockComponent
                .setContentView(view)
        super
                .setContentView(contentView)
    }

    override fun onDestroy() {
        lockComponent
                .onDestroy()
        super
                .onDestroy()
    }


}

