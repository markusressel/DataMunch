package de.markusressel.datamunch.extensions

import android.content.Context
import de.markusressel.datamunch.R
import java.util.*

/**
 * Created by Markus on 15.02.2018.
 */
fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

/**
 * Returns true if the current device is considered a tablet
 */
fun Context.isTablet(): Boolean {
    return resources
            .getBoolean(R.bool.is_tablet)
}

fun Throwable.prettyPrint(): String {
    val message = "${this.message}:\n" + "${this.stackTrace.joinToString(separator = "\n")}}"

    return message
}