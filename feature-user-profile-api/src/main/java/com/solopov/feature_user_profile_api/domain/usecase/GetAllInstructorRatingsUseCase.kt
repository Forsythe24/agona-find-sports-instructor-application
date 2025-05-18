package com.solopov.feature_user_profile_api.domain.usecase

import com.solopov.feature_user_profile_api.domain.model.Rating

interface GetAllInstructorRatingsUseCase {
    suspend operator fun invoke(instructorId: String): List<Rating>?
}
