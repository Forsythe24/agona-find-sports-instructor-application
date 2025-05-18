package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.model.Rating
import com.solopov.feature_user_profile_api.domain.usecase.GetAllInstructorRatingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllInstructorRatingsUseCaseImpl @Inject constructor(
    private val ratingRepository: RatingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : GetAllInstructorRatingsUseCase {
    override suspend fun invoke(instructorId: String): List<Rating>? {
        return withContext(dispatcher) {
            ratingRepository.getAllInstructorRatingsById(instructorId)
        }
    }
}
