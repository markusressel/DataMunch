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

package de.markusressel.datamunch.application

import android.os.Handler
import android.os.Looper
import de.markusressel.datamunch.view.activity.UnknownErrorDialog
import timber.log.Timber
import java.util.*

/**
 * Created by Markus on 22.02.2018.
 */
abstract class ExceptionHandlerApplicationBase : DaggerApplicationBase() {

    // Default System Handler for uncaught Exceptions
    private val originalUncaughtExceptionHandler: Thread.UncaughtExceptionHandler = Thread
            .getDefaultUncaughtExceptionHandler()

    init {
        // Set up our own UncaughtExceptionHandler to log errors we couldn't even think of
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                Timber.e(throwable, "FATAL EXCEPTION")

                val timeOfException = Date().time

                if (isUIThread()) {
                    applicationContext.startActivity(UnknownErrorDialog.getNewInstanceIntent(
                            applicationContext, throwable, timeOfException))
                } else {
                    //handle non UI thread throw uncaught exception
                    Handler(Looper.getMainLooper()).post {
                        applicationContext.startActivity(
                                UnknownErrorDialog.getNewInstanceIntent(
                                        applicationContext, throwable, timeOfException))
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error showing \"Unknown Error\" AlertDialog")
            } finally {
                System.exit(2) //Prevents the service/app from freezing
            }

            // not possible without killing all app processes, including the UnkownErrorDialog!?
            //                if (originalUncaughtExceptionHandler != null) {
            // Delegates to Android's error handling
            //                    originalUncaughtExceptionHandler.uncaughtException(thread, throwable);
            //                }
        }
    }

    /**
     * Check if the current is the UI thread
     *
     * @return true if the current thread is the same as the UI thread
     */
    private fun isUIThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

}