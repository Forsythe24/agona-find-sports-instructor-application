package com.solopov.feature_authentication_api.domain

interface AuthRepository {
    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    )

    suspend fun signInUser(email: String, password: String): Boolean

    suspend fun sendNewPassword(email: String)
}
