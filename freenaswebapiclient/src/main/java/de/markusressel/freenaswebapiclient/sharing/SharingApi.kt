package de.markusressel.freenaswebapiclient.sharing

import de.markusressel.freenaswebapiclient.sharing.afp.AfpApi
import de.markusressel.freenaswebapiclient.sharing.cifs.CifsApi
import de.markusressel.freenaswebapiclient.sharing.nfs.NfsApi

interface SharingApi : AfpApi, CifsApi, NfsApi