package de.markusressel.datamunch.view.fragment.system

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.jakewharton.rxbinding2.view.RxView
import com.ncorti.slidetoact.SlideToActView
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.component.LoadingComponent
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
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

    val loadingComponent by lazy {
        LoadingComponent(this)
    }

    override fun initComponents(context: Context) {
        super
                .initComponents(context)
        loadingComponent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val parent = super.onCreateView(inflater, container, savedInstanceState) as ViewGroup
        return loadingComponent
                .onCreateView(inflater, parent, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        loadingComponent
                .showContent()
    }

    override fun onResume() {
        super
                .onResume()

        RxView
                .clicks(buttonRestart)
                .bindToLifecycle(buttonRestart)
                .subscribe {
                    showWarningDialog(R.string.restart) {
                        freeNasWebApiClient
                                .rebootSystem()
                    }
                }

        RxView
                .clicks(buttonShutdown)
                .bindToLifecycle(buttonShutdown)
                .subscribe {
                    showWarningDialog(R.string.shutdown) {
                        freeNasWebApiClient
                                .shutdownSystem()
                    }
                }

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

                loadingComponent
                        .showLoading()

                Single
                        .fromCallable { function() }
                        .bindToLifecycle(button)
                        .subscribeBy(onSuccess = {
                            loadingComponent
                                    .showContent()
                            dialog
                                    .dismiss()
                        }, onError = {
                            loadingComponent
                                    .showError(it)
                        })
            }
        }
    }

}