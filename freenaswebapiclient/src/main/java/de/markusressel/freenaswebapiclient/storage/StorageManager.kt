package de.markusressel.freenaswebapiclient.storage

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.jail.VolumeApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetHandler
import de.markusressel.freenaswebapiclient.storage.volume.VolumeHandler

/**
 * Created by Markus on 09.02.2018.
 */
class StorageManager(private val requestManager: RequestManager,
                     datasetApi: DatasetApi = DatasetHandler(requestManager),
                     volumeApi: VolumeApi = VolumeHandler(requestManager)) :
    DatasetApi by datasetApi, VolumeApi by volumeApi, StorageApi