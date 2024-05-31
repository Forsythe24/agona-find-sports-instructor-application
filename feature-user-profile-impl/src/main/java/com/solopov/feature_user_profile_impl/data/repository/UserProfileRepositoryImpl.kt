package com.solopov.feature_user_profile_impl.data.repository

import com.solopov.common.data.remote.dao.UserRemoteDao
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor (
    private val userRemoteDao: UserRemoteDao,
    private val userMappers: UserMappers,
): UserProfileRepository {


    override suspend fun getUserByUid(uid: String): User {
        return userMappers.mapUserRemoteToUser(userRemoteDao.getUserByUid(uid))
    }

    override suspend fun getCurrentUser(): User {
        return userMappers.mapUserRemoteToUser(userRemoteDao.getCurrentUser())
    }

    override suspend fun updateUser(user: User) {
        return userRemoteDao.updateUser(userMappers.mapUserToUserRemote(user))
    }

    override suspend fun updateUserPassword(password: String) {
        return userRemoteDao.updateUserPassword(password)
    }

    override suspend fun uploadProfileImage(imageUri: String): String {
        return userRemoteDao.uploadProfileImage(imageUri)
    }

    override suspend fun verifyCredentials(password: String): Boolean {
        return userRemoteDao.verifyCredentials(password)
    }

    override suspend fun deleteProfile(): Boolean {
        return userRemoteDao.deleteProfile()
    }
}

