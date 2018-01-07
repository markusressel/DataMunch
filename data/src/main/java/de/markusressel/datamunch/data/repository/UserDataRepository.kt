package de.markusressel.datamunch.data.repository

import de.markusressel.datamunch.data.entity.mapper.UserEntityDataMapper
import de.markusressel.datamunch.data.repository.datasource.UserDataStore
import de.markusressel.datamunch.data.repository.datasource.UserDataStoreFactory
import de.markusressel.datamunch.domain.User
import de.markusressel.datamunch.domain.repository.UserRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * {@link UserRepository} for retrieving user data.
 *
 * Created by Markus on 06.01.2018.
 */
@Singleton
class UserDataRepository : UserRepository {

    @Inject
    lateinit var userDataStoreFactory: UserDataStoreFactory

    @Inject
    lateinit var userEntityDataMapper: UserEntityDataMapper

    override fun allUsers(): Observable<List<User>> {
        //we always get all allUsers from the cloud
        val userDataStore: UserDataStore = userDataStoreFactory.create()
        return userDataStore.userEntityList().map(this.userEntityDataMapper::transform)
    }

    override fun user(userId: Int): Observable<User> {
        val userDataStore: UserDataStore = userDataStoreFactory.create()
        return userDataStore.userEntityDetails(userId).map(this.userEntityDataMapper::transform)
    }

}