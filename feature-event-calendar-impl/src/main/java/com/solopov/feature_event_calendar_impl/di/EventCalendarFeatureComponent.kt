package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.network.di.RemoteApi
import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_event_calendar_api.di.EventCalendarFeatureApi
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import com.solopov.feature_event_calendar_impl.presentation.di.EventCalendarComponent
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    dependencies = [
        EventCalendarFeatureDependencies::class
    ],
    modules = [
        EventCalendarFeatureModule::class,
        EventCalendarFeatureUseCaseModule::class
    ]
)
interface EventCalendarFeatureComponent : EventCalendarFeatureApi {

    fun eventCalendarComponentFactory(): EventCalendarComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(router: EventCalendarRouter): Builder
        fun withDependencies(deps: EventCalendarFeatureDependencies): Builder

        fun build(): EventCalendarFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            RemoteApi::class,
            DbApi::class,
        ]
    )
    interface EventCalendarFeatureDependenciesComponent : EventCalendarFeatureDependencies
}
