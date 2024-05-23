package com.solopov.feature_event_calendar_impl.di

import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.remote.di.RemoteApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_event_calendar_impl.EventCalendarRouter
import javax.inject.Inject


@ApplicationScope
class EventCalendarFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val router: EventCalendarRouter
) : FeatureApiHolder(featureContainer) {


    //Помогаем даггеру создать компонент именно с теми зависимостями, которые нам нужны

    override fun initializeDependencies(): Any {
        val eventCalendarFeatureDependencies =
            DaggerEventCalendarFeatureComponent_EventCalendarFeatureDependenciesComponent.builder()
                .remoteApi(getFeature(RemoteApi::class.java))
                .dbApi(getFeature(DbApi::class.java))
                .commonApi(commonApi())
                .build()
        return DaggerEventCalendarFeatureComponent.builder()
            .withDependencies(eventCalendarFeatureDependencies)
            .router(router)
            .build()
    }
}
