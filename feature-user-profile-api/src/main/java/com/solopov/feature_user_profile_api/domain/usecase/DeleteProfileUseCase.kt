package com.solopov.feature_user_profile_api.domain.usecase

interface DeleteProfileUseCase {
    suspend operator fun invoke(): Boolean
}
