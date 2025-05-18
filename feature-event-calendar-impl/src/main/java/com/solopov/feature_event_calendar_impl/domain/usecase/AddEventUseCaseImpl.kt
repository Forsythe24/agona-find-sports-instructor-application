package com.solopov.feature_event_calendar_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.model.Event
import com.solopov.feature_event_calendar_api.domain.usecase.AddEventUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddEventUseCaseImpl @Inject constructor(
    private val eventCalendarRepository: EventCalendarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AddEventUseCase {
    override suspend fun invoke(event: Event) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.addEvent(event)
        }
    }
}
