package com.solopov.feature_authentication_api.domain.usecase

interface SignInUserUseCase {
    suspend operator fun invoke(email: String, password: String): Boolean
}
