package com.solopov.feature_event_calendar_api.domain.model


data class User(
    var id: String,
    var name: String,
    var age: Int,
    var gender: String,
    var sport: String?,
    var photo: String?,
    var experience: String?,
    var description: String?,
    var rating: Float?,
    var numberOfRatings: Int?,
    var hourlyRate: Float?,
    var isInstructor: Boolean = false
)




