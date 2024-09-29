package com.solopov.feature_authentication_api.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        return withContext(ioDispatcher) {
            authRepository.createUser(
                email,
                password,
                name,
                age,
                gender,
            )
        }
    }

    suspend fun signInUser(email: String, password: String): Boolean {
        return withContext(ioDispatcher) {
            authRepository.signInUser(email, password)
        }
    }

    suspend fun sendNewPassword(email: String) {
        return withContext(ioDispatcher) {
            authRepository.sendNewPassword(email)
        }
    }

}
