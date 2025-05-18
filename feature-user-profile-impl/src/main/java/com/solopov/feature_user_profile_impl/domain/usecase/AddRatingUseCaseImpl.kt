package com.solopov.feature_user_profile_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.model.Rating
import com.solopov.feature_user_profile_api.domain.usecase.AddRatingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddRatingUseCaseImpl @Inject constructor(
    private val ratingRepository: RatingRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : AddRatingUseCase {
    override suspend fun invoke(rating: Rating) {
        return withContext(dispatcher) {
            ratingRepository.addRating(rating)
        }
    }
}
