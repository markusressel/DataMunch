package de.markusressel.datamunch.view.fragment.system

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.ebanx.swipebtn.SwipeButton
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.LoadingSupportFragmentBase
import kotlinx.android.synthetic.main.fragment_maintenance.*


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class MaintenanceFragment : LoadingSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_maintenance

    override val optionsMenuRes: Int?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        buttonRestart
                .setOnClickListener {
                    showWarningDialog(R.string.swipe_to_restart) {
                        freeNasWebApiClient
                                .rebootSystem()
                    }
                }

        buttonShutdown
                .setOnClickListener {
                    showWarningDialog(R.string.swipe_to_shutdown) {
                        freeNasWebApiClient
                                .shutdownSystem()
                    }
                }

        showContent()
    }

    private fun showWarningDialog(@StringRes buttonText: Int, function: () -> Any) {
        val inflater = LayoutInflater
                .from(context)
        val dialogContentView = inflater
                .inflate(R.layout.dialog_danger_zone_warning_single_action, null)
        val button: SwipeButton = dialogContentView
                .findViewById(R.id.swipeButton)
        button
                .setText(getString(buttonText))
        button
                .setOnStateChangeListener {
                    if (it) {
                        function()
                    }
                }

        MaterialDialog
                .Builder(context as Context)
                .customView(dialogContentView, false)
                .title(R.string.warning)
                .neutralText(R.string.abort)
                .show()
    }

}