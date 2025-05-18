package com.solopov.feature_user_profile_api.domain.usecase

interface VerifyCredentialsUseCase {
    suspend operator fun invoke(password: String): Boolean
}
