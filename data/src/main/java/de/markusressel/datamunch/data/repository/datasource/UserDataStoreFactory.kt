package de.markusressel.datamunch.data.repository.datasource

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that creates different implementations of {@link UserDataStore}.
 *
 * Created by Markus on 06.01.2018.
 */
@Singleton
class UserDataStoreFactory {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var userDataStore: DiskUserDataStore

    /**
     * Create [UserDataStore] from a user id.
     */
    fun create(): UserDataStore {
        return userDataStore
    }

}