package com.solopov.feature_authentication_api.domain.usecase

interface RegisterUserUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String
    )
}
