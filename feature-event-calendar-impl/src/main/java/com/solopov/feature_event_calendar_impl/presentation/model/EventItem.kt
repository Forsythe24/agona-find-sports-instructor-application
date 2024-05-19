package com.solopov.feature_event_calendar_impl.presentation.model

import java.io.Serializable
import java.util.Date

data class EventItem (
    val id: Long,
    val name: String,
    val personName: String?,
    val date: Date,
    val startTime: Int?,
    val endTime: Int?,
    val place: String?,
): Serializable
