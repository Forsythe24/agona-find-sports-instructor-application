package com.solopov.feature_event_calendar_api.domain.model

data class Rating(
    var id: Int?,
    val instructorId: String,
    val userId: String,
    val rating: Float
)
