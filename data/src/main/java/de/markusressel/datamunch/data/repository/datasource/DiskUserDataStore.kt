package de.markusressel.datamunch.data.repository.datasource

import de.markusressel.datamunch.data.entity.UserEntity
import io.reactivex.Observable
import javax.inject.Singleton


/**
 * {@link UserDataStore} implementation based on file system data store.
 *
 * Created by Markus on 06.01.2018.
 */
@Singleton
class DiskUserDataStore : UserDataStore {

    override fun userEntityList(): Observable<List<UserEntity>> {
        //TODO: implement simple cache for storing/retrieving collections of allUsers.
        throw UnsupportedOperationException("Operation is not available!!!")
    }

    override fun userEntityDetails(userId: Int): Observable<UserEntity> {
        // TODO:
//        return this.userCache.get(userId)
        return Observable.create { }
    }

}