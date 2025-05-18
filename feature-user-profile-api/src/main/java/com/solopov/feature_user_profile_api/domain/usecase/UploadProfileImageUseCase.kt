package com.solopov.feature_user_profile_api.domain.usecase

interface UploadProfileImageUseCase {
    suspend operator fun invoke(imageUri: String): String
}
