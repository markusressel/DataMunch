package de.markusressel.datamunch.extensions

import java.util.*

/**
 * Created by Markus on 15.02.2018.
 */
fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start