package de.markusressel.freenaswebapiclient.account.user

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import de.markusressel.freenaswebapiclient.RequestManager
import io.reactivex.Single

/**
 * Created by Markus on 09.02.2018.
 */
class UserHandler(private val requestManager: RequestManager) : UserApi {

    override fun getUsers(limit: Int, offset: Int): Single<List<UserModel>> {
        val params = requestManager
                .createLimitOffsetParams(limit, offset)
        return requestManager
                .doRequest("/account/users/", params, Method.GET, UserModel.ListDeserializer())
    }

    override fun createUser(data: UserModel): Single<UserModel> {
        return requestManager
                .doJsonRequest("/account/users/", Method.POST, data, UserModel.SingleDeserializer())
    }

    override fun updateUser(data: UserModel): Single<UserModel> {
        return requestManager
                .doJsonRequest("/account/users/${data.id}/", Method.PUT, data,
                               UserModel.SingleDeserializer())
    }

    override fun deleteUser(user: UserModel): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/account/users/${user.id}/", Method.DELETE)
    }

    override fun setUserPassword(userId: Long,
                                 newPassword: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        val passwordMap = jsonObject("bsdusr_password" to newPassword)
        return requestManager
                .doJsonRequest("/account/users/$userId/password/", Method.POST, passwordMap)
    }

    override fun getGroups(userId: Long): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        return requestManager
                .doRequest("/account/users/$userId/groups/", Method.GET)
    }

    override fun setGroups(userId: Long,
                           vararg group: String): Single<Pair<Response, Result<ByteArray, FuelError>>> {
        val data = jsonArray(group.asList())
        return requestManager
                .doJsonRequest("/account/users/$userId/groups/", Method.GET, data)
    }

}