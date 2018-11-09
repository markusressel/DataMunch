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

package de.markusressel.datamunch.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import de.markusressel.commons.logging.prettyPrint
import de.markusressel.datamunch.R
import de.markusressel.datamunch.view.activity.base.DaggerSupportActivityBase
import kotlinx.android.synthetic.main.dialog_unknown_error.*
import timber.log.Timber
import java.util.*

/**
 * Shows a Dialog with details about an unknown Exception/Error that occurred during runtime
 *
 *
 * Created by Markus on 05.02.2016.
 */
class UnknownErrorDialog : DaggerSupportActivityBase() {

    override val layoutRes: Int
        get() = R.layout.dialog_unknown_error

    override val style: Int
        get() = DIALOG

    private var throwable: Throwable? = null
    private var timeRaised: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // do everything in a try statement to prevent repeating errors if something goes wrong while reporting the previous error
        try {
            super
                    .onCreate(savedInstanceState)

            setFinishOnTouchOutside(false) // prevent close dialog on touch outside window

            if (intent.hasExtra(THROWABLE_KEY)) {
                throwable = intent.getSerializableExtra(THROWABLE_KEY) as Throwable
            }
            if (intent.hasExtra(TIME_KEY)) {
                timeRaised = Date(intent.getLongExtra(TIME_KEY, 0))
            }

            //            buttonShareText!!
            //                    .setOnClickListener {
            //                        val intent = Intent()
            //                        intent
            //                                .action = Intent
            //                                .ACTION_SEND
            //                        intent
            //                                .putExtra(Intent.EXTRA_TEXT, LogHelper.getStackTraceText(throwable))
            //                        intent
            //                                .type = "text/plain"
            //                        startActivity(Intent.createChooser(intent, getString(R.string.send_to)))
            //                    }

            errorDescriptionEditText
                    .text = throwable?.prettyPrint() ?: "Missing throwable"

            //            if (smartphonePreferencesHandler.getValue(KEY_SEND_ANONYMOUS_CRASH_DATA)) {
            //                textView_automaticCrashReportingEnabledInfo!!
            //                        .visibility = View
            //                        .VISIBLE
            //                textView_automaticCrashReportingDisabledInfo!!
            //                        .visibility = View
            //                        .GONE
            //            } else {
            //                textView_automaticCrashReportingEnabledInfo!!
            //                        .visibility = View
            //                        .GONE
            //                textView_automaticCrashReportingDisabledInfo!!
            //                        .visibility = View
            //                        .VISIBLE
            //            }

            closeButton
                    .setOnClickListener { finish() }
        } catch (e: Exception) {
            Timber
                    .e(e)
        }
    }

    companion object {

        private const val THROWABLE_KEY = "throwable"
        private const val TIME_KEY = "time"

        /**
         * Create a new instance of this Dialog while providing an argument.
         *
         * @param context
         * @param t                        any throwable
         * @param timeRaisedInMilliseconds time when the throwable was raised
         */
        fun getNewInstanceIntent(context: Context, t: Throwable,
                                 timeRaisedInMilliseconds: Long): Intent {
            val intent = Intent(context, UnknownErrorDialog::class.java)
            intent
                    .flags = Intent
                    .FLAG_ACTIVITY_NEW_TASK
            intent
                    .putExtra(THROWABLE_KEY, t)
            intent
                    .putExtra(TIME_KEY, timeRaisedInMilliseconds)
            return intent
        }
    }

}
