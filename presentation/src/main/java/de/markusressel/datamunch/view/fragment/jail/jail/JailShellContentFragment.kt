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
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.ajalt.timberkt.Timber
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.data.ssh.SSHShell
import de.markusressel.datamunch.extensions.doAsync
import de.markusressel.datamunch.extensions.prettyPrint
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_jails_jail_shell.*
import rx.Subscription
import java.io.InputStream
import javax.inject.Inject


/**
 * Created by Markus on 18.02.2018.
 */
class JailShellContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jails_jail_shell

    private var shellText: String by savedInstanceState("")

    private var shellInstance: SSHShell? = null

    private var subscription: Subscription? = null

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)
        shellContentTextView
                .text = "Shell is initializing..."
        shellContentTextView
                .setOnClickListener {
                    openKeyboard()
                }
    }

    private fun openKeyboard() {
        context
                ?.let { context ->
                    val keyboard = context.getSystemService(
                            Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    keyboard
                            .showSoftInput(shellContentTextView, 0)
                }
    }

    override fun onResume() {
        super
                .onResume()

        Single
                .fromCallable {
                    shellInstance = frittenbudeServerManager
                            .getShell()
                    shellInstance?.connect()!!
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .bindUntilEvent(this, Lifecycle.Event.ON_STOP)
                .subscribeBy(onSuccess = { inputStream ->
                    // clear content text
                    appendToShellOutput("")

                    val entity = getEntityFromPersistence()
                    // enter jail
                    shellInstance
                            ?.writeToShell("jexec ${entity.jail_host} /bin/tcsh; exit", NEW_LINE)

                    shellInstance
                            ?.let {
                                while (it.isConnected()) {
                                    readInput(inputStream)

                                    try {
                                        Thread
                                                .sleep(100)
                                    } catch (ee: Exception) {
                                        Timber
                                                .e(ee)
                                    }
                                }
                            }
                }, onError = {
                    appendToShellOutput("\n\n${it.prettyPrint()}")
                })

        Bus
                .observe<KeyEvent>()
                .subscribe {
                    doAsync {
                        handleKeybordInput(it)
                    }
                }
                .registerInBus(this)
    }

    private fun handleKeybordInput(keyEvent: KeyEvent) {
        // only react to key down events
        if (keyEvent.action == KeyEvent.ACTION_DOWN) {
            when (keyEvent.keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    return
                }
                KeyEvent.KEYCODE_DEL -> {
                    shellInstance
                            ?.backspace()
                }
                KeyEvent.KEYCODE_ENTER -> {
                    shellInstance
                            ?.writeToShell(NEW_LINE)
                }
                else -> {
                    shellInstance
                            ?.writeToShell(keyEvent.stringRepresentation())
                }
            }
        }
    }

    private fun readInput(inputStream: InputStream) {
        val tmp = ByteArray(1024)
        while (inputStream.available() > 0) {
            val i: Int = inputStream
                    .read(tmp, 0, tmp.size)
            if (i < 0) break

            val arrayString = tmp
                    .contentToString()

            if ("8, 27, 91, 75" in arrayString) {
                // remove last character
                shellText = shellText
                        .dropLast(1)
                appendToShellOutput("")
            } else if (tmp[0] == 7.toByte()) {
                // ignore (tried backspace without available character)
            } else {
                val string = String(tmp, 0, i)
                        .replace("\b", "")
                appendToShellOutput(string)
            }
        }
    }

    override fun onStop() {
        super
                .onStop()
        subscription
                ?.unsubscribe()
        shellInstance
                ?.disconnect()
    }

    private fun appendToShellOutput(text: String) {
        activity
                ?.runOnUiThread {
                    shellText = "$shellText$text"

                    shellContentTextView
                            .text = shellText

                    scrollView
                            .post {
                                scrollView
                                        .fullScroll(View.FOCUS_DOWN)
                            }
                }
    }

    //    private fun printChar(keyEvent: KeyEvent) {
    //        val oldText = shellContentTextView
    //                .text
    //                .toString()
    //
    //        val pressedKey = keyEvent
    //                .stringRepresentation()
    //
    //        shellContentTextView
    //                .text = "$oldText$pressedKey"
    //    }

    private fun KeyEvent.stringRepresentation(): String {
        return this
                .getUnicodeChar(this.metaState)
                .toChar()
                .toString()
    }

    companion object {
        const val NEW_LINE = "\r\n"
    }

}