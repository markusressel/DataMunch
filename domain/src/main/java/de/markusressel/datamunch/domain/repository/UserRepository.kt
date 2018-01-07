package de.markusressel.datamunch.domain.repository

import de.markusressel.datamunch.domain.User
import io.reactivex.Observable

/**
 * Interface that represents a Repository for getting {@link User} related data.
 *
 * Created by Markus on 06.01.2018.
 */
interface UserRepository {

    /**
     * Get an {@link Observable} which will emit a List of {@link User}.
     */
    fun allUsers(): Observable<List<User>>

    /**
     * Get an {@link Observable} which will emit a {@link User}.
     *
     * @param userId The user id used to retrieve user data.
     */
    fun user(userId: Int): Observable<User>

}