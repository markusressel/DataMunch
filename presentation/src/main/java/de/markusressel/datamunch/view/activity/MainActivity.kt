package de.markusressel.datamunch.view.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.markusressel.datamunch.view.activity.base.NavigationDrawerActivity
import de.markusressel.datamunch.view.component.LockComponent

class MainActivity : NavigationDrawerActivity() {

    override val style: Int
        get() = DEFAULT

    private val lockComponent: LockComponent = LockComponent(this, { preferenceHandler })

    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun setContentView(layoutResID: Int) {
        super
                .setContentView(layoutResID)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super
                .setContentView(view, params)
    }

    override fun setContentView(view: View?) {
        val contentView = lockComponent
                .setContentView(view)
        super
                .setContentView(contentView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)
        lockComponent
                .onCreate(savedInstanceState)
    }

    override fun onResume() {
        super
                .onResume()
        lockComponent
                .onResume()
    }

    override fun onDestroy() {
        lockComponent
                .onDestroy()
        super
                .onDestroy()
    }


}

