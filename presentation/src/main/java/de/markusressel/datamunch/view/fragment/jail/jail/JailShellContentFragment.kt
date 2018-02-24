package de.markusressel.datamunch.view.fragment.jail.jail

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.freebsd.FreeBSDServerManager
import de.markusressel.datamunch.event.KeyDownEvent
import kotlinx.android.synthetic.main.content_jails_jail_shell.*
import javax.inject.Inject


/**
 * Created by Markus on 18.02.2018.
 */
class JailShellContentFragment : JailContentFragmentBase() {

    override val layoutRes: Int
        get() = R.layout.content_jails_jail_shell

    @Inject
    lateinit var frittenbudeServerManager: FreeBSDServerManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super
                .onViewCreated(view, savedInstanceState)

        //        frittenbudeServerManager
        //                .getShell()

        shellContentTextView
                .text = "Shell is initializing..."
        shellContentTextView
                .setOnClickListener {
                    openKeyboard()
                }
    }

    private fun openKeyboard() {
        val inputMethodManager = activity?.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager
                .toggleSoftInputFromWindow(shellContentTextView.applicationWindowToken,
                                           InputMethodManager.SHOW_FORCED, 0)

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
        updateUiFromEntity()

        Bus
                .observe<KeyDownEvent>()
                .subscribe {

                    when (it.keyCode) {
                        KeyEvent.KEYCODE_DEL -> {
                            shellContentTextView
                                    .text = shellContentTextView
                                    .text
                                    .dropLast(1)
                        }
                        else -> {
                            it
                                    .event
                                    ?.let {
                                        printChar(it)
                                    }
                        }
                    }
                }
                .registerInBus(this)
    }

    private fun printChar(keyEvent: KeyEvent) {
        val oldText = shellContentTextView
                .text
                .toString()

        val pressedKey = keyEvent
                .getUnicodeChar(keyEvent.metaState)
                .toChar()
                .toString()

        shellContentTextView
                .text = "$oldText$pressedKey"
    }

    private fun updateUiFromEntity() {
        val entity = getEntityFromPersistence()

    }


}