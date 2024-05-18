package com.solopov.feature_event_calendar_impl.presentation.model

import java.io.Serializable

data class EventCalendar (
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
    var isInstructor: Boolean
): Serializable
