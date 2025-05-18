package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_event_calendar_api.domain.EventCalendarRepository
import com.solopov.feature_event_calendar_impl.data.repository.EventCalendarRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface EventCalendarFeatureModule {

    @Binds
    @FeatureScope
    fun provideEventCalendarRepository(repository: EventCalendarRepositoryImpl): EventCalendarRepository
}
