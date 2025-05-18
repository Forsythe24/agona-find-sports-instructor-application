package com.solopov.feature_user_profile_api.domain.usecase

import com.solopov.feature_user_profile_api.domain.model.Rating

interface GetRatingByUserAndInstructorUseCase {
    suspend operator fun invoke(userId: String, instructorId: String): Rating?
}
