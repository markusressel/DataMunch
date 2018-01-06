package de.markusressel.datamunch.gui.fileuploader

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.presenatation.IconicsHelper
import de.markusressel.datamunch.view.activity.DaggerSupportActivityBase
import javax.inject.Inject


class FileUploaderActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_fileuploader

    @Inject
    lateinit var iconicsHelper: IconicsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}