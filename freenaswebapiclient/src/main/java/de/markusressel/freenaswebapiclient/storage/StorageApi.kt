package de.markusressel.freenaswebapiclient.storage

import de.markusressel.freenaswebapiclient.jails.jail.VolumeApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetApi
import de.markusressel.freenaswebapiclient.storage.disk.DiskApi
import de.markusressel.freenaswebapiclient.storage.scrub.ScrubApi
import de.markusressel.freenaswebapiclient.storage.snapshot.SnapshotApi
import de.markusressel.freenaswebapiclient.storage.task.TaskApi

/**
 * Created by Markus on 09.02.2018.
 */
interface StorageApi : DatasetApi, DiskApi, ScrubApi, SnapshotApi, TaskApi, VolumeApi