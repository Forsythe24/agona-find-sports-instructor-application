package com.solopov.feature_authentication_impl.di

import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.common.di.scope.FeatureScope
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_impl.data.mappers.UserMappers
import com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class AuthFeatureModule {

    @Provides
    @FeatureScope
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository = authRepository



    @Provides
    @FeatureScope
    fun provideAuthInteractor(authRepository: AuthRepository): AuthInteractor = AuthInteractor(authRepository)
}
