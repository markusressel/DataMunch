package de.markusressel.datamunch.view.activity

import de.markusressel.datamunch.R
import de.markusressel.datamunch.presenatation.IconicsHelper
import javax.inject.Inject


class FileUploaderActivity : DaggerSupportActivityBase() {

    override val style: Int
        get() = DIALOG

    override val layoutRes: Int
        get() = R.layout.activity_fileuploader

    @Inject
    lateinit var iconicsHelper: IconicsHelper

}