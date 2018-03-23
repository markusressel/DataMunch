package de.markusressel.datamunch.view.fragment.pages

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.FileUploadManager
import de.markusressel.datamunch.view.fragment.base.DaggerSupportFragmentBase
import de.markusressel.datamunch.view.fragment.base.TabNavigationFragment
import de.markusressel.datamunch.view.fragment.system.MaintenanceFragment
import de.markusressel.datamunch.view.fragment.system.alert.AlertsFragment
import de.markusressel.datamunch.view.fragment.system.update.UpdatesFragment
import javax.inject.Inject


class FileUploaderPage : TabNavigationFragment() {

    override val tabItems: List<Pair<Int, () -> DaggerSupportFragmentBase>>
        get() {
            return listOf(R.string.alerts to ::AlertsFragment,
                          R.string.updates to ::UpdatesFragment,
                          R.string.maintenance to ::MaintenanceFragment)
        }

    @Inject
    lateinit var fileUploadManager: FileUploadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super
                .onCreate(savedInstanceState)

        //        val rxPermissions = RxPermissions(activity!!)
        //
        //        // Must be done during an initialization phase like onCreate
        //        rxPermissions
        //                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //                .subscribe { granted ->
        //                    if (granted) { // Always true pre-M
        //                        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath + File.separator
        //                                + "Camera" + File.separator
        //                                + "IMG_20180116_164959.jpg")
        //                        val destinationPath = "/mnt/vol1/Media/Fotos/Markus/DataMunch/IMG_20180116_164959.jpg"
        //
        //                        Single.fromCallable {
        //                            frittenbudeServerManager.uploadFile(
        //                                    turrisSshConnectionConfig,
        //                                    frittenbudeSshConnectionConfig,
        //                                    file = file,
        //                                    destinationPath = destinationPath
        //                            )
        //                        }
        //                                .subscribeOn(Schedulers.io())
        //                                .observeOn(AndroidSchedulers.mainThread())
        //                                .subscribeBy(
        //                                        onSuccess = {
        //                                            val text = "Upload Success"
        //
        //                                            serverStatus.text = serverStatus.text.toString() + "\n\n" + text
        //                                        },
        //                                        onError = {
        //                                            serverStatus.text = serverStatus.text.toString() + "\n\n" + it.message
        //                                            Timber.e(it)
        //                                        }
        //                                )
        //                    } else {
        //                        Timber.e { "Missing permission" }
        //                    }
        //                }
    }

}