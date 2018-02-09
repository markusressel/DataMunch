package de.markusressel.freenaswebapiclient.account.group

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

interface GroupApi {
    /**
     * Get a list of all groups
     */
    fun getGroups(limit: Int = RequestManager.DEFAULT_LIMIT,
                  offset: Int = RequestManager.DEFAULT_OFFSET): Single<List<GroupModel>>

    /**
     * Create a new group
     */
    fun createGroup(data: GroupModel): Single<GroupModel>

    /**
     * Update an existing group
     */
    fun updateGroup(data: GroupModel): Single<GroupModel>

    /**
     * Delete a group
     */
    fun deleteGroup(group: GroupModel): Single<Pair<Response, Result<ByteArray, FuelError>>>
}