package de.markusressel.datamunch.data.entity.mapper

import de.markusressel.datamunch.data.entity.UserEntity
import de.markusressel.datamunch.domain.User
import javax.inject.Singleton


/**
 * Created by Markus on 06.01.2018.
 */
@Singleton
class UserEntityDataMapper {

    /**
     * Transform a {@link UserEntity} into an {@link User}.
     *
     * @param userEntity Object to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    fun transform(userEntity: UserEntity): User {
        val user: User = User(
                userEntity.id,
                userEntity.username,
                userEntity.fullName,
                userEntity.eMail,
                userEntity.icon
        )

        return user
    }

    /**
     * Transform a List of {@link UserEntity} into a Collection of {@link User}.
     *
     * @param userEntityCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link UserEntity} otherwise null.
     */
    fun transform(userEntityCollection: Collection<UserEntity>): List<User> {
        val userList = ArrayList<User>(20)
        userEntityCollection.mapNotNullTo(userList) { transform(it) }
        return userList
    }

}