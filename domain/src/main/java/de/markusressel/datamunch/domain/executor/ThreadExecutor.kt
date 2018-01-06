package de.markusressel.datamunch.domain.executor

import java.util.concurrent.Executor

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link com.fernandocejas.android10.sample.domain.interactor.UseCase} out of the UI thread.
 *
 * Created by Markus on 06.01.2018.
 */
interface ThreadExecutor : Executor