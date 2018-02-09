package de.markusressel.freenaswebapiclient.jails

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.jails.configuration.ConfigurationApi
import de.markusressel.freenaswebapiclient.jails.configuration.ConfigurationHandler
import de.markusressel.freenaswebapiclient.jails.jail.JailApi
import de.markusressel.freenaswebapiclient.jails.jail.JailHandler
import de.markusressel.freenaswebapiclient.jails.mountpoint.MountpointApi
import de.markusressel.freenaswebapiclient.jails.mountpoint.MountpointHandler
import de.markusressel.freenaswebapiclient.jails.template.TemplateApi
import de.markusressel.freenaswebapiclient.jails.template.TemplateHandler

/**
 * Created by Markus on 09.02.2018.
 */
class JailsManager(private val requestManager: RequestManager,
                   configurationApi: ConfigurationApi = ConfigurationHandler(requestManager),
                   jailApi: JailApi = JailHandler(requestManager),
                   mountpointApi: MountpointApi = MountpointHandler(requestManager),
                   templateApi: TemplateApi = TemplateHandler(requestManager)) :
    ConfigurationApi by configurationApi, JailApi by jailApi, MountpointApi by mountpointApi,
    TemplateApi by templateApi, JailsApi