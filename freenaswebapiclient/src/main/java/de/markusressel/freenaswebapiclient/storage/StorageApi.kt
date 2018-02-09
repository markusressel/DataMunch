package de.markusressel.freenaswebapiclient.storage

import de.markusressel.freenaswebapiclient.jails.jail.VolumeApi
import de.markusressel.freenaswebapiclient.storage.dataset.DatasetApi

/**
 * Created by Markus on 09.02.2018.
 */
interface StorageApi : DatasetApi, VolumeApi