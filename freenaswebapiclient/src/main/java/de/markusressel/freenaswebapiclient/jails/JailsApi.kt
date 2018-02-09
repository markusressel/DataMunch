package de.markusressel.freenaswebapiclient.jails

import de.markusressel.freenaswebapiclient.jails.jail.JailApi
import de.markusressel.freenaswebapiclient.jails.mountpoint.MountpointApi
import de.markusressel.freenaswebapiclient.jails.template.TemplateApi

/**
 * Created by Markus on 09.02.2018.
 */
interface JailsApi : JailApi, MountpointApi, TemplateApi {}