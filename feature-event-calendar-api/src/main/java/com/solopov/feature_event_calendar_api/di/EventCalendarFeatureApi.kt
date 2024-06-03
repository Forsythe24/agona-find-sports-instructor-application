package com.solopov.feature_event_calendar_api.di

import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarInteractor
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarRepository
import com.solopov.feature_event_calendar_api.domain.interfaces.RatingRepository

interface EventCalendarFeatureApi {

    fun provideEventCalendarInteractor(): EventCalendarInteractor
    fun provideEventCalendarRepository(): EventCalendarRepository
    fun provideRatingRepository(): RatingRepository
}
