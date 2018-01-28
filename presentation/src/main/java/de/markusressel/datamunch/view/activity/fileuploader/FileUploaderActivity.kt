package de.markusressel.datamunch.view.activity.fileuploader

import android.os.Bundle
import de.markusressel.datamunch.R
import de.markusressel.datamunch.data.FileUploadManager
import de.markusressel.datamunch.navigation.DrawerItemHolder
import de.markusressel.datamunch.view.activity.NavigationDrawerActivity
import javax.inject.Inject

class FileUploaderActivity : NavigationDrawerActivity() {

    @Inject
    lateinit var fileUploadManager: FileUploadManager

    override val style: Int
        get() = DEFAULT

    override val layoutRes: Int
        get() = R.layout.activity_fileuploader

    override fun getInitialNavigationDrawerSelection(): Long {
        return DrawerItemHolder.FileUploader.identifier
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.menu_item_file_uploader)

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