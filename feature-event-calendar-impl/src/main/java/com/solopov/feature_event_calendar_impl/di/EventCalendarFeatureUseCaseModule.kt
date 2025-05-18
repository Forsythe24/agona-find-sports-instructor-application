package com.solopov.feature_event_calendar_impl.di

import com.solopov.feature_event_calendar_api.domain.usecase.AddEventUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteAllRecentEventsUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.DeleteEventUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllEventsByDateUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetAllPossiblePartnersNamesUseCase
import com.solopov.feature_event_calendar_api.domain.usecase.GetCurrentUserIdUseCase
import com.solopov.feature_event_calendar_impl.domain.usecase.AddEventUseCaseImpl
import com.solopov.feature_event_calendar_impl.domain.usecase.DeleteAllRecentEventsUseCaseImpl
import com.solopov.feature_event_calendar_impl.domain.usecase.DeleteEventUseCaseImpl
import com.solopov.feature_event_calendar_impl.domain.usecase.GetAllEventsByDateUseCaseImpl
import com.solopov.feature_event_calendar_impl.domain.usecase.GetAllPossiblePartnersNamesUseCaseImpl
import com.solopov.feature_event_calendar_impl.domain.usecase.GetCurrentUserIdUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface EventCalendarFeatureUseCaseModule {
    @Binds
    fun bindAddEventUseCase(impl: AddEventUseCaseImpl): AddEventUseCase

    @Binds
    fun bindGetAllEventsByDateUseCase(impl: GetAllEventsByDateUseCaseImpl): GetAllEventsByDateUseCase

    @Binds
    fun bindGetAllPossiblePartnersNamesUseCase(
        impl: GetAllPossiblePartnersNamesUseCaseImpl
    ): GetAllPossiblePartnersNamesUseCase

    @Binds
    fun bindDeleteEventUseCase(impl: DeleteEventUseCaseImpl): DeleteEventUseCase

    @Binds
    fun bindGetCurrentUserIdUseCase(impl: GetCurrentUserIdUseCaseImpl): GetCurrentUserIdUseCase

    @Binds
    fun bindDeleteAllRecentEventsUseCase(
        impl: DeleteAllRecentEventsUseCaseImpl
    ): DeleteAllRecentEventsUseCase
}
