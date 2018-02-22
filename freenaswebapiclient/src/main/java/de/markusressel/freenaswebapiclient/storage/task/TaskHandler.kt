package de.markusressel.freenaswebapiclient.storage.task

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class TaskHandler(private val requestManager: RequestManager) : TaskApi {

    override fun getTasks(limit: Int, offset: Int): Single<List<TaskModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/storage/task/", params, Method.GET, TaskModel.ListDeserializer())
    }

    override fun createTask(data: TaskModel): Single<TaskModel> {
        return requestManager
                .doJsonRequest("/storage/task/", Method.POST, data, TaskModel.SingleDeserializer())
    }

    override fun updateTask(data: TaskModel): Single<TaskModel> {
        return requestManager
                .doJsonRequest("/storage/task/${data.id}/", Method.PUT, data,
                               TaskModel.SingleDeserializer())
    }

    override fun deleteTask(taskId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/storage/task/$taskId/", Method.DELETE)
    }

}