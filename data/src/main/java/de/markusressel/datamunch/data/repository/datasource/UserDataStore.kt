package de.markusressel.datamunch.data.repository.datasource

import de.markusressel.datamunch.data.entity.UserEntity
import io.reactivex.Observable


/**
 * Interface that represents a data store from where data is retrieved.
 *
 * Created by Markus on 06.01.2018.
 */
interface UserDataStore {

    /**
     * Get an [Observable] which will emit a List of [UserEntity].
     */
    fun userEntityList(): Observable<List<UserEntity>>

    /**
     * Get an [Observable] which will emit a [UserEntity] by its id.
     *
     * @param userId The id to retrieve user data.
     */
    fun userEntityDetails(userId: Int): Observable<UserEntity>

}