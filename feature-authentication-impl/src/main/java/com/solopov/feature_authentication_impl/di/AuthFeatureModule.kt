package com.solopov.feature_authentication_impl.di

import com.solopov.common.di.coroutine.qualifier.IoDispatcher
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_authentication_api.domain.AuthInteractor
import com.solopov.feature_authentication_api.domain.AuthRepository
import com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class AuthFeatureModule {

    @Provides
    @FeatureScope
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository


    @Provides
    @FeatureScope
    fun provideAuthInteractor(
        authRepository: AuthRepository,
        @IoDispatcher
        ioDispatcher: CoroutineDispatcher,
    ): AuthInteractor =
        AuthInteractor(authRepository, ioDispatcher)
}
