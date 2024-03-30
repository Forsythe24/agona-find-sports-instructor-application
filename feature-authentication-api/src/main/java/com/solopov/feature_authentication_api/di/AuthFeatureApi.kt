package com.solopov.feature_authentication_api.di

import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository

interface AuthFeatureApi {
    fun provideAuthInteractor(): AuthInteractor
    fun provideAuthRepository(): AuthRepository
}
