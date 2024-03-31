package com.solopov.feature_authentication_api.domain.interfaces

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AuthInteractor(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        return withContext(dispatcher) {
            authRepository.createUser(
                email,
                password,
                name,
                age,
                gender,
            )
        }
    }

    suspend fun signInUser(email: String, password: String) {
        return withContext(dispatcher) {
            authRepository.signInUser(email, password)
        }
    }

}
