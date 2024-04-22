package com.solopov.feature_user_profile_api.domain.interfaces

import com.solopov.feature_user_profile_api.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class  UserProfileInteractor(
    private val userProfileRepository: UserProfileRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getUserByUid(uid: String): User {
        return withContext(dispatcher) {
            userProfileRepository.getUserByUid(uid)
        }
    }

    suspend fun getCurrentUser(): User {
        return withContext(dispatcher) {
            userProfileRepository.getCurrentUser()
        }
    }

    suspend fun updateUser(user: User) {
        return withContext(dispatcher) {
            userProfileRepository.updateUser(user)
        }
    }

    suspend fun updateUserPassword(password: String) {
        return withContext(dispatcher) {
            userProfileRepository.updateUserPassword(password)
        }
    }

}