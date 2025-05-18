package com.solopov.feature_event_calendar_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteEventUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteEventUseCaseImpl @Inject constructor(
    private val eventCalendarRepository: EventCalendarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : DeleteEventUseCase {
    override suspend fun invoke(id: Long) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.deleteEventById(id)
        }
    }
}
