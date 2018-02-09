package de.markusressel.freenaswebapiclient.account.group

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class GroupHandler(private val requestManager: RequestManager) : GroupApi {

    override fun getGroups(limit: Int, offset: Int): Single<List<GroupModel>> {
        return requestManager
                .doRequest("/account/groups/", Method.GET, GroupModel.ListDeserializer())
    }

    override fun createGroup(data: GroupModel): Single<GroupModel> {
        return requestManager
                .doJsonRequest("/account/groups/", Method.POST, data,
                               GroupModel.SingleDeserializer())
    }

    override fun updateGroup(data: GroupModel): Single<GroupModel> {
        return requestManager
                .doJsonRequest("/account/groups/${data.id}/", Method.PUT, data,
                               GroupModel.SingleDeserializer())
    }

    override fun deleteGroup(
            group: GroupModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/account/groups/${group.id}/", Method.DELETE)
    }

}