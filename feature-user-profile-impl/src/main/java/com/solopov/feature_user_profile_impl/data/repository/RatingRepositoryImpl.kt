package com.solopov.feature_user_profile_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.feature_user_profile_api.domain.RatingRepository
import com.solopov.feature_user_profile_api.domain.model.Rating
import com.solopov.feature_user_profile_impl.data.mappers.RatingMappers
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor(
    private val ratingMappers: RatingMappers,
    private val db: AppDatabase,
) : RatingRepository {
    override suspend fun addRating(rating: Rating) {
        db.ratingDao().addRating(ratingMappers.mapRatingToRatingLocal(rating))
    }

    override suspend fun getAllInstructorRatingsById(instructorId: String): List<Rating>? {
        return db.ratingDao().getAllInstructorRatingsById(instructorId)?.map(ratingMappers::mapRatingLocalToRating)
    }

    override suspend fun getRatingByUserAndInstructorIds(userId: String, instructorId: String): Rating? {
        val rating = db.ratingDao().getRatingByUserAndInstructorIds(userId, instructorId)
        return if (rating != null) {
            ratingMappers.mapRatingLocalToRating(rating)
        } else {
            null
        }
    }
}
