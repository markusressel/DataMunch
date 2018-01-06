package de.markusressel.datamunch.domain.executor

import io.reactivex.Scheduler

/**
 * Thread abstraction created to change the execution context from any thread to any other thread.
 * Useful to encapsulate a UI Thread for example, since some job will be done in background, an
 * implementation of this interface will change context and update the UI.
 *
 * Created by Markus on 06.01.2018.
 */
interface PostExecutionThread {

    fun getScheduler(): Scheduler
}