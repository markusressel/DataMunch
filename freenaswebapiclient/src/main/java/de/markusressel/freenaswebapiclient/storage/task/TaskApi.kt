package de.markusressel.freenaswebapiclient.storage.task

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 22.02.2018.
 */
interface TaskApi {

    /**
     * Get a list of all tasks
     */
    fun getTasks(limit: Int = RequestManager.DEFAULT_LIMIT,
                 offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<TaskModel>>

    /**
     * Create a new task
     */
    fun createTask(data: TaskModel): Single<TaskModel>

    /**
     * Update an existing task
     */
    fun updateTask(data: TaskModel): Single<TaskModel>

    /**
     * Delete an existing task
     */
    fun deleteTask(taskId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>>
}