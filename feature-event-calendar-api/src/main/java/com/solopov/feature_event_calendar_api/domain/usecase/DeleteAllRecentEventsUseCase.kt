package com.solopov.feature_event_calendar_api.domain.usecase

import java.util.Date

interface DeleteAllRecentEventsUseCase {
    suspend operator fun invoke(date: Date)
}
