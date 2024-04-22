package com.solopov.feature_authentication_api.domain.interfaces

import com.solopov.feature_authentication_api.domain.model.User

interface AuthRepository {
    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): User

    suspend fun signInUser(email: String?, password: String?): Boolean
}
