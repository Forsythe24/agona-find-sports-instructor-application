package com.solopov.feature_event_calendar_impl.presentation.model

import java.io.Serializable
import java.util.Date

data class EventItem(
    var id: Long,
    var name: String,
    var personName: String?,
    val date: Date,
    var startTime: Int,
    var endTime: Int,
    var place: String?,
) : Serializable
