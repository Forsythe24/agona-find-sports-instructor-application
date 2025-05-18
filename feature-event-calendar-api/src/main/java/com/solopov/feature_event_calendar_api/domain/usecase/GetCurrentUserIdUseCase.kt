package com.solopov.feature_event_calendar_api.domain.usecase

interface GetCurrentUserIdUseCase {
    suspend operator fun invoke(): String
}
