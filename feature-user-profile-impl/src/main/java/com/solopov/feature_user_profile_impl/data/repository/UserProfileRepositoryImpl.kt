package com.solopov.feature_user_profile_impl.data.repository

import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor (
    private val userFirebaseDao: UserFirebaseDao,
    private val userMappers: UserMappers
): UserProfileRepository {


    override suspend fun getUserByUid(uid: String): User {
        return userMappers.mapUserFirebaseToUser(userFirebaseDao.getUserByUid(uid))
    }

    override suspend fun getCurrentUser(): User {
        return userMappers.mapUserFirebaseToUser(userFirebaseDao.getCurrentUser())
    }

    override suspend fun updateUser(user: User) {
        return userFirebaseDao.updateUser(userMappers.mapUserToUserFirebase(user))
    }

    override suspend fun updateUserPassword(password: String) {
        return userFirebaseDao.updateUserPassword(password)

    }
}
