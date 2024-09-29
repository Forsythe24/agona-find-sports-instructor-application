package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_event_calendar_api.domain.EventCalendarInteractor
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_impl.data.repository.EventCalendarRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class EventCalendarFeatureModule {

    @Provides
    @FeatureScope
    fun provideEventCalendarRepository(repository: EventCalendarRepositoryImpl): EventCalendarRepository =
        repository

    @Provides
    @FeatureScope
    fun provideEventCalendarInteractor(
        eventCalendarRepository: EventCalendarRepository,
        @IoDispatcher
        ioDispatcher: CoroutineDispatcher,
    ): EventCalendarInteractor =
        EventCalendarInteractor(eventCalendarRepository, ioDispatcher)
}
