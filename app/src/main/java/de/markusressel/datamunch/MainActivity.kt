package de.markusressel.datamunch

import android.os.Bundle
import com.mikepenz.materialdrawer.DrawerBuilder
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DrawerBuilder().withActivity(this).build()
    }
}
