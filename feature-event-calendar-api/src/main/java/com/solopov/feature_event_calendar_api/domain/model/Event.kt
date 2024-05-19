package com.solopov.feature_event_calendar_api.domain.model

import java.util.Date

data class Event (
    val id: Long,
    val name: String,
    val personName: String?,
    val date: Date,
    val startTime: Int?,
    val endTime: Int?,
    val place: String?,
)
