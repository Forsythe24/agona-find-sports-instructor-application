package com.solopov.feature_event_calendar_impl.presentation.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solopov.common.di.viewmodel.ViewModelKey
import com.solopov.common.di.viewmodel.ViewModelModule
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_event_calendar_api.domain.interfaces.EventCalendarInteractor
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import com.solopov.feature_event_calendar_impl.data.mappers.EventMappers
import com.solopov.feature_event_calendar_impl.presentation.EventCalendarViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class EventCalendarModule {

    @Provides
    fun provideMainViewModel(fragment: Fragment, factory: ViewModelProvider.Factory): EventCalendarViewModel {
        return ViewModelProvider(fragment, factory)[EventCalendarViewModel::class.java]
    }

    @Provides
    @IntoMap
    @ViewModelKey(EventCalendarViewModel::class)
    fun provideInstructorViewModel(interactor: EventCalendarInteractor, exceptionHandlerDelegate: ExceptionHandlerDelegate, eventMappers: EventMappers, eventCalendarRouter: EventCalendarRouter): ViewModel {
        return EventCalendarViewModel(interactor, exceptionHandlerDelegate, eventMappers, eventCalendarRouter)
    }
}
