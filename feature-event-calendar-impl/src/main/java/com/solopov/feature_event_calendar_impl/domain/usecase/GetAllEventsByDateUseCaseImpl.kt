package com.solopov.feature_event_calendar_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllEventsByDateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class GetAllEventsByDateUseCaseImpl @Inject constructor(
    private val eventCalendarRepository: EventCalendarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GetAllEventsByDateUseCase {
    override suspend fun invoke(date: Date): List<Event>? {
        return withContext(ioDispatcher) {
            eventCalendarRepository.getAllEventsByDate(date)
        }
    }
}
