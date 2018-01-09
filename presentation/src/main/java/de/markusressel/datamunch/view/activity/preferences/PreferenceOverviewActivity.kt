package de.markusressel.datamunch.view.activity.preferences

import android.content.Intent
import android.preference.PreferenceFragment
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.preferences.PreferenceHandler
import de.markusressel.datamunch.navigation.Navigator
import de.markusressel.typedpreferences.PreferenceItem
import javax.inject.Inject

/**
 * Created by Markus on 15.07.2017.
 */
class PreferenceOverviewActivity : PreferenceActivityBase() {

    @Inject
    lateinit var navigator: Navigator

    var themeListener: ((PreferenceItem<Int>, Int, Int) -> Unit)? = null
    var localeListener: ((PreferenceItem<Int>, Int, Int) -> Unit)? = null

    override fun onStart() {
        super.onStart()

        setListeners()
    }

    override fun onCreateNavigation(fragment: PreferenceFragment) {
        super.onCreateNavigation(fragment)
        fragment.addPreferencesFromResource(R.xml.preferences)
    }

    private fun setListeners() {
        themeListener = preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.THEME) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }

        localeListener = preferenceHandler.addOnPreferenceChangedListener(PreferenceHandler.LOCALE) { preferenceItem: PreferenceItem<Int>, old: Int, new: Int ->
            restartActivity()
        }
    }

    private fun restartActivity() {
        finish()

        navigator.navigateTo(this,
                Navigator.NavigationPages.MainPage,
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        navigator.navigateTo(this,
                Navigator.NavigationPages.PreferencesOverviewPage)
    }

    override fun onStop() {
        super.onStop()

        themeListener?.let {
            preferenceHandler.removeOnPreferenceChangedListener(it)
        }

        localeListener?.let {
            preferenceHandler.removeOnPreferenceChangedListener(it)
        }
    }

}
