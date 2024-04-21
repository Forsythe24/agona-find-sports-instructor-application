package com.solopov.feature_user_profile_impl.di

import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureDependencies
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureModule
import com.solopov.feature_user_profile_impl.presentation.user_profile.di.UserProfileComponent
import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.presentation.edit_profile.di.EditProfileComponent
import com.solopov.feature_user_profile_impl.presentation.instruct.di.InstructApplicationComponent
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(
    dependencies = [
        UserProfileFeatureDependencies::class
    ],
    modules = [
        UserProfileFeatureModule::class
    ]
)
interface UserProfileFeatureComponent: UserProfileFeatureApi {

    fun userProfileComponentFactory(): UserProfileComponent.Factory
    fun instructApplicationComponentFactory(): InstructApplicationComponent.Factory
    fun editProfileComponentFactory(): EditProfileComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(router: UserProfileRouter): Builder
        fun withDependencies(deps: UserProfileFeatureDependencies): Builder

        fun build(): UserProfileFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            FirebaseApi:: class
        ]
    )
    interface UserProfileFeatureDependenciesComponent : UserProfileFeatureDependencies
}
