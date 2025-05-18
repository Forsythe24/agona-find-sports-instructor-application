package com.solopov.feature_authentication_impl.di

import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_authentication_api.domain.AuthRepository
import com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface AuthFeatureModule {

    @Binds
    @FeatureScope
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository
}
