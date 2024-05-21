package com.solopov.feature_event_calendar_impl.data.repository

import com.solopov.common.data.db.AppDatabase
import com.solopov.feature_event_calendar_api.domain.model.Rating
import com.solopov.feature_event_calendar_impl.data.mappers.RatingMappers
import com.solopov.feature_event_calendar_api.domain.interfaces.RatingRepository
import javax.inject.Inject

class RatingRepositoryImpl @Inject constructor (
    private val ratingMappers: RatingMappers,
    private val db: AppDatabase,
): RatingRepository {
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
