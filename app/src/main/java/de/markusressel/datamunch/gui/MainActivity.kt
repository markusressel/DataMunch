package de.markusressel.datamunch.gui

import android.content.Intent
import android.os.Bundle
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import de.markusressel.datamunch.R
import de.markusressel.datamunch.dagger.DaggerSupportActivityBase
import de.markusressel.datamunch.gui.preferences.PreferenceOverviewActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        DrawerBuilder().withActivity(this)
                .addDrawerItems(
                        DividerDrawerItem(),
                        PrimaryDrawerItem()
                                .withName(R.string.menu_item_settings)
                                .withOnDrawerItemClickListener { view, i, iDrawerItem ->
                                    val intent = Intent(this, PreferenceOverviewActivity::class.java)
                                    startActivity(intent)
                                    false
                                },
                        DividerDrawerItem()
                )
                .withToolbar(toolbar)
                .build()
    }
}