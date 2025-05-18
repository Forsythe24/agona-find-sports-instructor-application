package com.solopov.feature_authentication_impl.di

import com.solopov.feature_authentication_api.domain.usecase.RegisterUserUseCase
import com.solopov.feature_authentication_api.domain.usecase.SendNewPasswordUseCase
import com.solopov.feature_authentication_api.domain.usecase.SignInUserUseCase
import com.solopov.feature_authentication_impl.domain.usecase.RegisterUserUseCaseImpl
import com.solopov.feature_authentication_impl.domain.usecase.SendNewPasswordUseCaseImpl
import com.solopov.feature_authentication_impl.domain.usecase.SignInUserUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface AuthFeatureUseCaseModule {
    @Binds
    fun bindRegisterUserUseCase(impl: RegisterUserUseCaseImpl): RegisterUserUseCase

    @Binds
    fun bindSignInUserUseCase(impl: SignInUserUseCaseImpl): SignInUserUseCase

    @Binds
    fun bindSendNewPasswordUseCase(impl: SendNewPasswordUseCaseImpl): SendNewPasswordUseCase
}
