package com.solopov.feature_event_calendar_api.domain.usecase

interface DeleteEventUseCase {
    suspend operator fun invoke(id: Long)
}
