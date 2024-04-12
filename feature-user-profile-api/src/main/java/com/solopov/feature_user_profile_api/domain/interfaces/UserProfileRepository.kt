package com.solopov.feature_user_profile_api.domain.interfaces

import com.solopov.feature_user_profile_api.domain.model.User


interface UserProfileRepository {
    suspend fun getUserByUid(uid: String): User
    suspend fun getCurrentUser(): User
}
