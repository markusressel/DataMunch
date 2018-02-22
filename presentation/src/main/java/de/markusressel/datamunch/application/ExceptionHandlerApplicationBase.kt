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
        Thread
                .setDefaultUncaughtExceptionHandler { thread, throwable ->
                    try {
                        Timber
                                .e(throwable, "FATAL EXCEPTION")

                        val timeOfException = Date()
                                .time

                        if (isUIThread()) {
                            applicationContext
                                    .startActivity(UnknownErrorDialog.getNewInstanceIntent(
                                            applicationContext, throwable, timeOfException))
                        } else {
                            //handle non UI thread throw uncaught exception
                            Handler(Looper.getMainLooper())
                                    .post {
                                        applicationContext
                                                .startActivity(
                                                        UnknownErrorDialog.getNewInstanceIntent(
                                                                applicationContext, throwable,
                                                                timeOfException))
                                    }
                        }
                    } catch (e: Exception) {
                        Timber
                                .e(e, "Error showing \"Unknown Error\" AlertDialog")
                    } finally {
                        System
                                .exit(2) //Prevents the service/app from freezing
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