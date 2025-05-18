package com.solopov.feature_user_profile_api.domain.usecase

import com.solopov.feature_user_profile_api.domain.model.Rating

interface AddRatingUseCase {
    suspend operator fun invoke(rating: Rating)
}
