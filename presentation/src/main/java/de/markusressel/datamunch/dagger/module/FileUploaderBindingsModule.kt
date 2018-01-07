package de.markusressel.datamunch.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.markusressel.datamunch.view.activity.fileuploader.FileUploaderActivity

/**
 * Created by Markus on 20.12.2017.
 */
@Module
abstract class FileUploaderBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun fileUploaderActivity(): FileUploaderActivity

}