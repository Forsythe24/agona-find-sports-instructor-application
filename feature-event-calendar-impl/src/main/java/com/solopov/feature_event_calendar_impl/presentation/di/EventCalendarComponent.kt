package com.solopov.feature_event_calendar_impl.presentation.di

import androidx.fragment.app.Fragment
import com.solopov.common.di.scope.ScreenScope
import com.solopov.feature_event_calendar_impl.presentation.EventCalendarFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        EventCalendarModule::class
    ]
)
@ScreenScope
interface EventCalendarComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance fragment: Fragment
        ): EventCalendarComponent
    }

    fun inject(fragment: EventCalendarFragment)
}
