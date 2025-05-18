package com.solopov.feature_event_calendar_impl.domain.usecase

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllPossiblePartnersNamesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllPossiblePartnersNamesUseCaseImpl @Inject constructor(
    private val eventCalendarRepository: EventCalendarRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : GetAllPossiblePartnersNamesUseCase {
    override suspend fun invoke(userId: String): List<String>? {
        return withContext(ioDispatcher) {
            eventCalendarRepository.getAllPossiblePartnersNamesByUserId(userId)
        }
    }
}
