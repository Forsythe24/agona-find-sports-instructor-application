package com.solopov.feature_authentication_impl.di

import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpComponent
import dagger.Component


@FeatureScope
@Component(
    dependencies = [
        AuthFeatureDependencies::class
    ],
    modules = [
        AuthFeatureModule::class
    ]
)
interface AuthFeatureComponent: AuthFeatureApi {

    fun signUpComponentFactory(): SignUpComponent.Factory

    @Component.Builder
    interface Builder {
        fun withDependencies(deps: AuthFeatureDependencies): Builder

        fun build(): AuthFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            FirebaseApi:: class
        ]
    )
    interface AuthFeatureDependenciesComponent : AuthFeatureDependencies
}