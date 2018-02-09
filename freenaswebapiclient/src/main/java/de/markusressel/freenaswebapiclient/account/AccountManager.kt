package de.markusressel.freenaswebapiclient.account

import de.markusressel.freenaswebapiclient.RequestManager
import de.markusressel.freenaswebapiclient.account.group.GroupApi
import de.markusressel.freenaswebapiclient.account.group.GroupHandler
import de.markusressel.freenaswebapiclient.account.user.UserApi
import de.markusressel.freenaswebapiclient.account.user.UserHandler

/**
 * Delegation class for encapsulating api calls into subclasses
 *
 * Created by Markus on 09.02.2018.
 */
class AccountManager(requestManager: RequestManager, userApi: UserApi = UserHandler(requestManager),
                     groupApi: GroupApi = GroupHandler(requestManager)) : UserApi by userApi,
    GroupApi by groupApi, AccountApi