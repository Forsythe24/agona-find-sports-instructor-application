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

}
