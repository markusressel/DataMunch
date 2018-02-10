package de.markusressel.freenaswebapiclient.sharing

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.sharing.afp.AfpApi
import de.markusressel.freenaswebapiclient.sharing.afp.AfpShareHandler
import de.markusressel.freenaswebapiclient.sharing.cifs.CifsApi
import de.markusressel.freenaswebapiclient.sharing.cifs.CifsShareHandler
import de.markusressel.freenaswebapiclient.sharing.nfs.NfsApi
import de.markusressel.freenaswebapiclient.sharing.nfs.NfsShareHandler

/**
 * Created by Markus on 10.02.2018.
 */
class SharingManager(private val requestManager: RequestManager,
                     afpApi: AfpApi = AfpShareHandler(requestManager),
                     cifsApi: CifsApi = CifsShareHandler(requestManager),
                     nfsApi: NfsApi = NfsShareHandler(requestManager)) : SharingApi,
    AfpApi by afpApi, CifsApi by cifsApi, NfsApi by nfsApi