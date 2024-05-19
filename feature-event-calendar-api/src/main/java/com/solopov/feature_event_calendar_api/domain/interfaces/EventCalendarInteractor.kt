package com.solopov.feature_event_calendar_api.domain.interfaces

import com.solopov.feature_event_calendar_api.domain.model.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date


class EventCalendarInteractor(
    private val eventCalendarRepository: EventCalendarRepository,
    private val ratingRepository: RatingRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun addEvent(event: Event) {
        return withContext(dispatcher) {
            eventCalendarRepository.addEvent(event)
        }
    }

    suspend fun getAllEventsByDate(date: Date): List<Event>? {
        return withContext(dispatcher) {
            eventCalendarRepository.getAllEventsByDate(date)
        }
    }

    suspend fun getAllPossiblePartnersNamesByUserId(userId: String): List<String>? {
        return withContext(dispatcher) {
            eventCalendarRepository.getAllPossiblePartnersNamesByUserId(userId)
        }
    }

    suspend fun getCurrentUserId(): String {
        return withContext(dispatcher) {
            eventCalendarRepository.getCurrentUserId()
        }
    }
}
