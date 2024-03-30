package com.solopov.feature_instructor_impl.di

import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.presentation.list.di.InstructorsComponent
import com.solopov.feature_instructor_impl.presentation.details.di.InstructorComponent
import dagger.BindsInstance
import dagger.Component

@FeatureScope
@Component(
    dependencies = [
        InstructorFeatureDependencies::class
    ],
    modules = [
        InstructorFeatureModule::class
    ]
)
interface  InstructorFeatureComponent : InstructorFeatureApi {

    fun instructorsComponentFactory(): InstructorsComponent.Factory

    fun instructorComponentFactory(): InstructorComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun router(instructorsRouter: InstructorsRouter): Builder

        fun withDependencies(deps: InstructorFeatureDependencies): Builder

        fun build(): InstructorFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            DbApi::class
        ]
    )
    interface InstructorFeatureDependenciesComponent : InstructorFeatureDependencies
}