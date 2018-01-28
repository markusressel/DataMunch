package de.markusressel.datamunch.data.freebsd.freenas.webapi

import de.markusressel.datamunch.data.freebsd.data.Jail
import javax.inject.Inject

/**
 * Created by Markus on 29.01.2018.
 */
class FreeNasWebApiManager @Inject constructor() {

    fun parseJailList(jsonString: String): List<Jail> {
        return emptyList()
    }

}