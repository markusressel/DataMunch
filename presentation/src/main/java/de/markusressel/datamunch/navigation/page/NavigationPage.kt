package de.markusressel.datamunch.navigation.page

import android.content.Context
import android.content.Intent

/**
 * Created by Markus on 08.01.2018.
 */
class NavigationPage(private val pageClass: Class<*>?) {

    fun createIntent(context: Context): Intent {
        return Intent(context, pageClass)
    }

}