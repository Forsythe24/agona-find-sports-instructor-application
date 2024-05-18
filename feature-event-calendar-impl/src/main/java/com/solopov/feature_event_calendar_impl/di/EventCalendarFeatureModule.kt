package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarInteractor
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarRepository
import com.solopov.feature_event_calendar_impl.data.repository.EventCalendarRepositoryImpl
import com.solopov.feature_event_calendar_impl.data.repository.RatingRepositoryImpl
import com.solopov.feature_event_calendar_api.domain.interfaces.RatingRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class EventCalendarFeatureModule {

    @Provides
    @FeatureScope
    fun provideEventCalendarRepository(repository: EventCalendarRepositoryImpl): EventCalendarRepository = repository

    @Provides
    @FeatureScope
    fun provideRatingRepository(repository: RatingRepositoryImpl): RatingRepository = repository

    @Provides
    @FeatureScope
    fun provideEventCalendarInteractor(eventCalendarRepository: EventCalendarRepository, ratingRepository: RatingRepository): EventCalendarInteractor = EventCalendarInteractor(eventCalendarRepository, ratingRepository, Dispatchers.IO)
}
