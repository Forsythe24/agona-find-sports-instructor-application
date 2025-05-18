package com.solopov.feature_authentication_api.domain.usecase

interface SendNewPasswordUseCase {
    suspend operator fun invoke(email: String)
}
