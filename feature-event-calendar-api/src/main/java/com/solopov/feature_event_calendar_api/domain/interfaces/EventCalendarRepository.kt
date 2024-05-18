package com.solopov.feature_event_calendar_api.domain.interfaces

import com.solopov.feature_event_calendar_api.domain.model.User

interface EventCalendarRepository {
    suspend fun getUserByUid(uid: String): User
    suspend fun getCurrentUser(): User
    suspend fun updateUser(user: User)
    suspend fun updateUserPassword(password: String)
    suspend fun uploadProfileImage(imageUri: String): String
    suspend fun verifyCredentials(password: String): Boolean
}
