package com.solopov.feature_event_calendar_api.domain.usecase

import com.solopov.feature_event_calendar_api.domain.model.Event

interface AddEventUseCase {
    suspend operator fun invoke(event: Event)
}
