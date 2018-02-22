package de.markusressel.freenaswebapiclient.storage

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.jail.VolumeApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetHandler
import de.markusressel.freenaswebapiclient.storage.disk.DiskApi
import de.markusressel.freenaswebapiclient.storage.disk.DiskHandler
import de.markusressel.freenaswebapiclient.storage.scrub.ScrubApi
import de.markusressel.freenaswebapiclient.storage.scrub.ScrubHandler
import de.markusressel.freenaswebapiclient.storage.snapshot.SnapshotApi
import de.markusressel.freenaswebapiclient.storage.snapshot.SnapshotHandler
import de.markusressel.freenaswebapiclient.storage.volume.VolumeHandler

/**
 * Created by Markus on 09.02.2018.
 */
class StorageManager(private val requestManager: RequestManager,
                     datasetApi: DatasetApi = DatasetHandler(requestManager),
                     diskApi: DiskApi = DiskHandler(requestManager),
                     scrubApi: ScrubApi = ScrubHandler(requestManager),
                     snapshotApi: SnapshotApi = SnapshotHandler(requestManager),
                     volumeApi: VolumeApi = VolumeHandler(requestManager)) :
    DatasetApi by datasetApi, DiskApi by diskApi, ScrubApi by scrubApi, SnapshotApi by snapshotApi,
    VolumeApi by volumeApi, StorageApi