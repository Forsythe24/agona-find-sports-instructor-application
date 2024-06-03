package com.solopov.feature_event_calendar_api.domain.interfaces

import com.solopov.feature_event_calendar_api.domain.model.Rating

interface RatingRepository {
    suspend fun addRating(rating: Rating)
    suspend fun getAllInstructorRatingsById(instructorId: String): List<Rating>?
    suspend fun getRatingByUserAndInstructorIds(userId: String, instructorId: String): Rating?
}
