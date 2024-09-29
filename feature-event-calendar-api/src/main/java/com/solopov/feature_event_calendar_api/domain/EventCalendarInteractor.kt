package com.solopov.feature_event_calendar_api.domain

import com.solopov.feature_event_calendar_api.domain.model.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date


class EventCalendarInteractor(
    private val eventCalendarRepository: EventCalendarRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun addEvent(event: Event) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.addEvent(event)
        }
    }

    suspend fun getAllEventsByDate(date: Date): List<Event>? {
        return withContext(ioDispatcher) {
            eventCalendarRepository.getAllEventsByDate(date)
        }
    }

    suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String>? {
        return withContext(ioDispatcher) {
            eventCalendarRepository.getAllPossiblePartnersNamesByUserId(userId)
        }
    }

    suspend fun deleteEventById(id: Long) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.deleteEventById(id)
        }
    }

    suspend fun getCurrentUserId(): String {
        return withContext(ioDispatcher) {
            eventCalendarRepository.getCurrentUserId()
        }
    }

    suspend fun deleteAllEventsThreeOrMoreDaysAgo(date: Date) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.deleteAllEventsThreeOrMoreDaysAgo(date)
        }
    }
}
