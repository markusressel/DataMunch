/*
 * DataMunch by Markus Ressel
 * Copyright (c) 2018.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.markusressel.datamunch.view.fragment.jail.jail

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.persistence.entity.JailEntity
import de.markusressel.datamunch.data.persistence.entity.asEntity
import de.markusressel.datamunch.data.persistence.entity.isRunning
import de.markusressel.datamunch.data.persistence.entity.isStopped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_jails_jail_detail.*
import java.util.concurrent.TimeUnit

/**
 * Created by Markus on 15.02.2018.
 */
class JailDetailContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jails_jail_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        RxView
                .clicks(startStopButton)
                .bindToLifecycle(startStopButton)
                .subscribeBy(onNext = {
                    val entity = getEntityFromPersistence()

                    when {
                        entity.isRunning() -> stopJail(entity)
                        entity.isStopped() -> startJail(entity)
                        else -> restartJail(entity)
                    }

                    (activity as JailDetailActivity)
                })

        RxView
                .longClicks(startStopButton)
                .bindToLifecycle(startStopButton)
                .subscribeBy(onNext = {
                    val entity = getEntityFromPersistence()

                    restartJail(entity)
                })

    }

    override fun onResume() {
        super
                .onResume()
        updateUiFromEntity()
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

        if (entity.isRunning()) {
            startStopButton
                    .setImageDrawable(iconHandler.getFabIcon(MaterialDesignIconic.Icon.gmi_stop))
        } else {
            startStopButton
                    .setImageDrawable(iconHandler.getFabIcon(MaterialDesignIconic.Icon.gmi_play))
        }

        idTextView
                .text = "${entity.id}"

        nameTextView
                .text = entity
                .jail_host

        statusTextView
                .text = entity
                .jail_status

        ipv4TextView
                .text = entity
                .jail_ipv4

        macTextView
                .text = entity
                .jail_mac
    }

    private fun startJail(jail: JailEntity) {
        freeNasWebApiClient
                .startJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    Toast
                            .makeText(activity, "Success!", Toast.LENGTH_SHORT)
                            .show()

                    // TODO: Show response

                    reloadJailsFromSource()
                }, onError = {
                    Toast
                            .makeText(activity, "Error! " + it.message, Toast.LENGTH_LONG)
                            .show()

                    // TODO: Show error
                    //                    loadingComponent
                    //                            .showError(it)
                })
    }

    private fun stopJail(jail: JailEntity) {
        freeNasWebApiClient
                .stopJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    Toast
                            .makeText(activity, "Success!", Toast.LENGTH_SHORT)
                            .show()
                    // TODO: Show response

                    reloadJailsFromSource()
                }, onError = {
                    Toast
                            .makeText(activity, "Error! " + it.message, Toast.LENGTH_LONG)
                            .show()

                    // TODO: Show error
                    //                    loadingComponent
                    //                            .showError(it)
                })
    }

    private fun restartJail(jail: JailEntity) {
        freeNasWebApiClient
                .restartJail(jail.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    Toast
                            .makeText(activity, "Success!", Toast.LENGTH_SHORT)
                            .show()
                    // TODO: Show response
                    reloadJailsFromSource()
                }, onError = {
                    Toast
                            .makeText(activity, "Error! " + it.message, Toast.LENGTH_LONG)
                            .show()


                    // TODO: Show error
                    //                    loadingComponent
                    //                            .showError(it)
                })
    }

    private fun reloadJailsFromSource() {
        freeNasWebApiClient
                .getJails()
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .delaySubscription(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    val entity = getEntityFromPersistence()
                    for (jailModel in it) {
                        if (jailModel.id == entity.id) {
                            getPersistenceHandler()
                                    .standardOperation()
                                    .put(jailModel.asEntity(entity.entityId))
                            break
                        }
                    }

                    updateUiFromEntity()
                }, onError = {
                    Toast
                            .makeText(activity, "Error! " + it.message, Toast.LENGTH_LONG)
                            .show()
                })
    }

}