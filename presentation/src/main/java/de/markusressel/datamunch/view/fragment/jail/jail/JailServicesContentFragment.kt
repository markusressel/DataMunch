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

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Markus on 18.02.2018.
 */
class JailServicesContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jails_jail_services

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    val servicesList: MutableList<JailService> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super
                .onResume()
        updateUi()
    }

    private fun updateUi() {
        Single
                .fromCallable {
                    val result = frittenbudeServerManager
                            .executeInJail(getEntityFromPersistence().jail_host, "service -l")
                    result[result.lastKey()]!!
                            .split(Regex.fromLiteral("\r\n"))
                            .dropLast(1)
                }
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = { services ->
                    Timber
                            .d {
                                services
                                        .joinToString(",")
                            }

                    servicesList
                            .clear()
                    servicesList
                            .addAll(services.map { JailService(name = it) })
                }, onError = {
                    Timber
                            .e(it)
                })
    }

}