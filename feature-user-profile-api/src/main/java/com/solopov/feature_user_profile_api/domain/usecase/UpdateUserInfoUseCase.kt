package com.solopov.feature_user_profile_api.domain.usecase

import com.solopov.feature_user_profile_api.domain.model.User

interface UpdateUserInfoUseCase {
    suspend operator fun invoke(user: User)
}
