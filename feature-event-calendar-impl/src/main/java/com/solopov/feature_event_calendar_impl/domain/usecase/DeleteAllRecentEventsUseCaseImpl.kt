package com.solopov.feature_event_calendar_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteAllRecentEventsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class DeleteAllRecentEventsUseCaseImpl @Inject constructor(
    private val eventCalendarRepository: EventCalendarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : DeleteAllRecentEventsUseCase {
    override suspend fun invoke(date: Date) {
        return withContext(ioDispatcher) {
            eventCalendarRepository.deleteAllEventsThreeOrMoreDaysAgo(date)
        }
    }
}
