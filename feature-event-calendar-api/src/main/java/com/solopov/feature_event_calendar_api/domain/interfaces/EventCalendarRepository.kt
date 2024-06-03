package com.solopov.feature_event_calendar_api.domain.interfaces

import com.solopov.feature_event_calendar_api.domain.model.Event
import java.util.Date

interface EventCalendarRepository {
    suspend fun addEvent(event: Event)
    suspend fun getAllEventsByDate(date: Date): List<Event>?
    suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String>?
    suspend fun deleteEventById(id: Long)
    suspend fun getCurrentUserId(): String

    suspend fun deleteAllEventsThreeOrMoreDaysAgo(date: Date)
}
