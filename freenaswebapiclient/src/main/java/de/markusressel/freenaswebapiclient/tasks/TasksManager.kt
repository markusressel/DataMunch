package de.markusressel.freenaswebapiclient.tasks

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.tasks.smart.SMARTTaskApi
import de.markusressel.freenaswebapiclient.tasks.smart.SMARTTaskHandler

/**
 * Created by Markus on 09.02.2018.
 */
class TasksManager(private val requestManager: RequestManager,
                   smartTaskApi: SMARTTaskApi = SMARTTaskHandler(requestManager)) : TasksApi,
    SMARTTaskApi by smartTaskApi