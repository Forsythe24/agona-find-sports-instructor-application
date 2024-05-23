package com.solopov.feature_authentication_impl.di

import com.solopov.common.data.remote.di.RemoteApi
import com.solopov.common.di.CommonApi
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.presentation.login.di.LogInComponent
import com.solopov.feature_authentication_impl.presentation.password_recovery.di.PasswordRecoveryComponent
import com.solopov.feature_authentication_impl.presentation.signup.di.SignUpComponent
import dagger.BindsInstance
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
interface AuthFeatureComponent : AuthFeatureApi {

    fun signUpComponentFactory(): SignUpComponent.Factory
    fun logInComponentFactory(): LogInComponent.Factory

    fun passwordRecoveryComponentFactory(): PasswordRecoveryComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun router(authRouter: AuthRouter): Builder
        fun withDependencies(deps: AuthFeatureDependencies): Builder

        fun build(): AuthFeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
            RemoteApi::class
        ]
    )
    interface AuthFeatureDependenciesComponent : AuthFeatureDependencies
}
