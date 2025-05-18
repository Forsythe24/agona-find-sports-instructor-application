package com.solopov.feature_user_profile_api.domain.usecase

interface UpdateUserPasswordUseCase {
    suspend operator fun invoke(password: String)
}
