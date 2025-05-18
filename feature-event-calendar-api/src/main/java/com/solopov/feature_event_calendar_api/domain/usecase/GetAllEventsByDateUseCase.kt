package com.solopov.feature_event_calendar_api.domain.usecase

import com.solopov.feature_event_calendar_api.domain.model.Event
import java.util.Date

interface GetAllEventsByDateUseCase {
    suspend operator fun invoke(date: Date): List<Event>?
}
