package de.markusressel.datamunch.view.fragment.system

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.ncorti.slidetoact.SlideToActView
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.plugin.LoadingPlugin
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_maintenance.*


/**
 * Server Status fragment
 *
 * Created by Markus on 07.01.2018.
 */
class MaintenanceFragment : DaggerSupportFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.fragment_maintenance

    val loadingPlugin = LoadingPlugin()

    init {
        addPlugin(loadingPlugin)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        buttonRestart
                .setOnClickListener {
                    showWarningDialog(R.string.restart) {
                        freeNasWebApiClient
                                .rebootSystem()
                    }
                }

        buttonShutdown
                .setOnClickListener {
                    showWarningDialog(R.string.shutdown) {
                        freeNasWebApiClient
                                .shutdownSystem()
                    }
                }

        loadingPlugin
                .showContent()
    }

    private fun showWarningDialog(@StringRes buttonText: Int, function: () -> Any) {
        val inflater = LayoutInflater
                .from(context)
        val dialogContentView = inflater
                .inflate(R.layout.dialog_danger_zone_warning_single_action, null)

        val dialog = MaterialDialog
                .Builder(context as Context)
                .customView(dialogContentView, false)
                .title(R.string.warning)
                .neutralText(R.string.abort)
                .show()

        val button: SlideToActView = dialogContentView
                .findViewById(R.id.swipeButton)
        button
                .text = getString(buttonText)
        button
                .onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                dialog
                        .dismiss()

                loadingPlugin
                        .showLoading()

                Single
                        .fromCallable { function() }
                        .subscribeBy(onSuccess = {
                            loadingPlugin
                                    .showContent()
                            dialog
                                    .dismiss()
                        }, onError = {
                            loadingPlugin
                                    .showError(it)
                        })
            }
        }
    }

}