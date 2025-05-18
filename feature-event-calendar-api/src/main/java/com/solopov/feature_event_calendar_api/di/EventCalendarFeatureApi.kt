package com.solopov.feature_event_calendar_api.di

import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository

interface EventCalendarFeatureApi {
    fun provideEventCalendarRepository(): EventCalendarRepository
}
