package com.solopov.feature_user_profile_api.domain.interfaces

import com.solopov.feature_user_profile_api.domain.model.User

interface UserProfileRepository {
    suspend fun getUserByUid(uid: String): User
    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User)
    suspend fun updateUserPassword(password: String)
    suspend fun uploadProfileImage(imageUri: String): String
    suspend fun verifyCredentials(password: String): Boolean
    suspend fun deleteProfile(): Boolean
}
