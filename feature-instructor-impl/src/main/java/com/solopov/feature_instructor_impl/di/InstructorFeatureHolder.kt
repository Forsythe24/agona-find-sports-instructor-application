package com.solopov.feature_instructor_impl.di

import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.network.di.RemoteApi
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.feature_instructor_impl.InstructorsRouter
import javax.inject.Inject

@ApplicationScope
class InstructorFeatureHolder @Inject constructor(
    featureContainer: FeatureContainer,
    private val instructorsRouter: InstructorsRouter
) : FeatureApiHolder(featureContainer) {


    //Помогаем даггеру создать компонент именно с теми зависимостями, которые нам нужны

    override fun initializeDependencies(): Any {
        val instructorFeatureDependencies = DaggerInstructorFeatureComponent_InstructorFeatureDependenciesComponent.builder()
            .commonApi(commonApi())
            .dbApi(getFeature(DbApi::class.java))
            .remoteApi(getFeature(RemoteApi::class.java))
            .build()
        return DaggerInstructorFeatureComponent.builder()
            .withDependencies(instructorFeatureDependencies)
            .router(instructorsRouter)
            .build()
    }
}
